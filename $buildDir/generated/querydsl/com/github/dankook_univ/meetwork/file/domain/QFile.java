package com.github.dankook_univ.meetwork.file.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFile is a Querydsl query type for File
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFile extends EntityPathBase<File> {

    private static final long serialVersionUID = -1184220306L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFile file = new QFile("file");

    public final com.github.dankook_univ.meetwork.common.domain.QCore _super = new com.github.dankook_univ.meetwork.common.domain.QCore(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final ComparablePath<java.util.UUID> id = _super.id;

    public final StringPath key = createString("key");

    public final StringPath mime = createString("mime");

    public final StringPath name = createString("name");

    public final EnumPath<FileType> type = createEnum("type", FileType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.github.dankook_univ.meetwork.profile.domain.QProfile uploader;

    public QFile(String variable) {
        this(File.class, forVariable(variable), INITS);
    }

    public QFile(Path<? extends File> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFile(PathMetadata metadata, PathInits inits) {
        this(File.class, metadata, inits);
    }

    public QFile(Class<? extends File> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.uploader = inits.isInitialized("uploader") ? new com.github.dankook_univ.meetwork.profile.domain.QProfile(forProperty("uploader"), inits.get("uploader")) : null;
    }

}

