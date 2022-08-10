package com.github.dankook_univ.meetwork.post.infra.http.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PostCreateRequest {

    @NotNull
    @NotEmpty
    String title;

    @NotNull
    @NotEmpty
    String content;

    @NotNull
    @NotEmpty
    String boardId;

    List<MultipartFile> files;
}
