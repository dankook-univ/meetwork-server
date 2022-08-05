package com.github.dankook_univ.meetwork.common.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCore is a Querydsl query type for Core
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QCore extends EntityPathBase<Core> {

    private static final long serialVersionUID = -1586803456L;

    public static final QCore core = new QCore("core");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QCore(String variable) {
        super(Core.class, forVariable(variable));
    }

    public QCore(Path<? extends Core> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCore(PathMetadata metadata) {
        super(Core.class, metadata);
    }

}

