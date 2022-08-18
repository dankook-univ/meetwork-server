package com.github.dankook_univ.meetwork.file.domain;

import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.file.infra.http.response.FileResponse;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends Core {

    @ManyToOne(targetEntity = Profile.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id")
    private Member uploader;

    @Column(name = "file_key")
    private String key;

    @Enumerated(EnumType.STRING)
    private FileType type;

    private String mime;

    private String name;

    @Builder
    public File(Member uploader, FileType type, String mime, String name) {
        this.uploader = uploader;
        this.key = type + "/" + UUID.randomUUID() + "." + mime;
        this.type = type;
        this.mime = mime;
        this.name = name;
    }

    public FileResponse toResponse() {
        return FileResponse.builder()
            .file(this)
            .build();
    }
}