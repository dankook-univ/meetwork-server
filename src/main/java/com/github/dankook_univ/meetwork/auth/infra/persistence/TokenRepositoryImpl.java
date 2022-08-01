package com.github.dankook_univ.meetwork.auth.infra.persistence;

import com.github.dankook_univ.meetwork.auth.domain.token.Token;
import com.github.dankook_univ.meetwork.auth.domain.token.TokenRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final RedisTemplate<String, Token> redisTemplate;

    @Override
    public Token save(String key, Token token, Long timeToLive) {
        getOperations().set(key, token, Duration.ofMillis(timeToLive));
        return token;
    }

    @Override
    public void delete(String key) {
        getOperations().getAndDelete(key);
    }

    @Override
    public Token getByKey(String key) {
        return getOperations().get(key);
    }

    private ValueOperations<String, Token> getOperations() {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Token.class));
        return redisTemplate.opsForValue();
    }
}
