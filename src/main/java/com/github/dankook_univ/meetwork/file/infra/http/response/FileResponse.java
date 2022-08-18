package com.github.dankook_univ.meetwork.file.infra.http.response;

import com.github.dankook_univ.meetwork.file.domain.File;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileResponse {

    String id;

    String url;

    @Builder
    public FileResponse(
        File file
    ) {
        this.id = file.getId().toString();
        this.url = "http://220.149.242.12:9000/" + file.getKey();
    }
}
