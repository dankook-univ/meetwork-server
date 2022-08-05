package com.github.dankook_univ.meetwork.auth.domain.auth;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuth is a Querydsl query type for Auth
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuth extends EntityPathBase<Auth> {

    private static final long serialVersionUID = -1965593328L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuth auth = new QAuth("auth");

    public final com.github.dankook_univ.meetwork.common.domain.QCore _super = new com.github.dankook_univ.meetwork.common.domain.QCore(this);

    public final StringPath clientId = createString("clientId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final ComparablePath<java.util.UUID> id = _super.id;

    public final com.github.dankook_univ.meetwork.member.domain.QMember member;

    public final ListPath<com.github.dankook_univ.meetwork.auth.domain.role.Role, com.github.dankook_univ.meetwork.auth.domain.role.QRole> roles = this.<com.github.dankook_univ.meetwork.auth.domain.role.Role, com.github.dankook_univ.meetwork.auth.domain.role.QRole>createList("roles", com.github.dankook_univ.meetwork.auth.domain.role.Role.class, com.github.dankook_univ.meetwork.auth.domain.role.QRole.class, PathInits.DIRECT2);

    public final EnumPath<AuthType> type = createEnum("type", AuthType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QAuth(String variable) {
        this(Auth.class, forVariable(variable), INITS);
    }

    public QAuth(Path<? extends Auth> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuth(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuth(PathMetadata metadata, PathInits inits) {
        this(Auth.class, metadata, inits);
    }

    public QAuth(Class<? extends Auth> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.github.dankook_univ.meetwork.member.domain.QMember(forProperty("member")) : null;
    }

}

