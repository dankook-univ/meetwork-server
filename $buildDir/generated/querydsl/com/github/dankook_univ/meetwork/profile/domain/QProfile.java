package com.github.dankook_univ.meetwork.profile.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProfile is a Querydsl query type for Profile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProfile extends EntityPathBase<Profile> {

    private static final long serialVersionUID = -583500258L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProfile profile = new QProfile("profile");

    public final com.github.dankook_univ.meetwork.common.domain.QCore _super = new com.github.dankook_univ.meetwork.common.domain.QCore(this);

    public final StringPath bio = createString("bio");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.github.dankook_univ.meetwork.event.domain.QEvent event;

    //inherited
    public final ComparablePath<java.util.UUID> id = _super.id;

    public final BooleanPath isAdmin = createBoolean("isAdmin");

    public final com.github.dankook_univ.meetwork.member.domain.QMember member;

    public final StringPath nickname = createString("nickname");

    public final com.github.dankook_univ.meetwork.file.domain.QFile profileImage;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProfile(String variable) {
        this(Profile.class, forVariable(variable), INITS);
    }

    public QProfile(Path<? extends Profile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProfile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProfile(PathMetadata metadata, PathInits inits) {
        this(Profile.class, metadata, inits);
    }

    public QProfile(Class<? extends Profile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.event = inits.isInitialized("event") ? new com.github.dankook_univ.meetwork.event.domain.QEvent(forProperty("event"), inits.get("event")) : null;
        this.member = inits.isInitialized("member") ? new com.github.dankook_univ.meetwork.member.domain.QMember(forProperty("member")) : null;
        this.profileImage = inits.isInitialized("profileImage") ? new com.github.dankook_univ.meetwork.file.domain.QFile(forProperty("profileImage"), inits.get("profileImage")) : null;
    }

}

