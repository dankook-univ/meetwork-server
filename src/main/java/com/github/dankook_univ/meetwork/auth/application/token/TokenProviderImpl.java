package com.github.dankook_univ.meetwork.auth.application.token;

import com.github.dankook_univ.meetwork.auth.domain.auth.Auth;
import com.github.dankook_univ.meetwork.auth.domain.role.Role;
import com.github.dankook_univ.meetwork.auth.domain.token.Token;
import com.github.dankook_univ.meetwork.auth.exceptions.NotFoundAuthException;
import com.github.dankook_univ.meetwork.auth.infra.persistence.AuthRepositoryImpl;
import com.github.dankook_univ.meetwork.auth.infra.persistence.TokenRepositoryImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class TokenProviderImpl implements TokenProvider {

    private final Key key;
    private final Long ACCESS_TOKEN_EXPIRE_DATE = 1000 * 60 * 60 * 24L;
    private final Long REFRESH_TOKEN_EXPIRE_DATE = 1000 * 60 * 60 * 24 * 30L;

    private final TokenRepositoryImpl tokenRepository;
    private final AuthRepositoryImpl authRepository;

    public TokenProviderImpl(
        @Value("${jwt.secret}") String secret,
        @Autowired TokenRepositoryImpl tokenRepository,
        @Autowired AuthRepositoryImpl authRepository
    ) {
        byte[] bytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(bytes);
        this.tokenRepository = tokenRepository;
        this.authRepository = authRepository;
    }

    @Override
    public Token create(Auth auth) {
        Long currentTime = new Date().getTime();

        String accessToken = Jwts.builder()
            .setClaims(setClaims(auth))
            .setSubject(auth.getMember().getId().toString())
            .setExpiration(new Date(currentTime + ACCESS_TOKEN_EXPIRE_DATE))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        String refreshToken = Jwts.builder()
            .setClaims(setClaims(auth))
            .setSubject(auth.getMember().getId().toString())
            .setExpiration(new Date(currentTime + REFRESH_TOKEN_EXPIRE_DATE))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        Token token = Token.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();

        return tokenRepository.save(auth.getId().toString(), token, REFRESH_TOKEN_EXPIRE_DATE);
    }

    @Override
    public boolean remove(Auth auth) {
        try {
            tokenRepository.delete(auth.getId().toString());
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public boolean validation(String token) {
        return getClaims(token).getExpiration().after(new Date());
    }

    @Override
    public Auth parse(String token) {
        UUID memberId = UUID.fromString(getClaims(token).get("memberId", String.class));
        return authRepository.getByMemberId(memberId).orElseThrow(NotFoundAuthException::new);
    }

    @Override
    public Authentication getAuthentication(String accessToken) {
        Claims claims = getClaims(accessToken);
        if (claims.get("roles") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(
                    claims.get("roles").toString()
                        .replaceAll("^\\[", "")
                        .replaceAll("]$", "")
                        .split(", ")
                ).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private Claims setClaims(Auth auth) {
        Claims claims = Jwts.claims();
        claims.put("memberId", auth.getMember().getId());
        claims.put("roles",
            auth.getRoles().stream().map(Role::getType).collect(Collectors.toList()));
        return claims;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}