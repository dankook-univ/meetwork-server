package com.github.dankook_univ.meetwork.post.comment.infra.http;

import com.github.dankook_univ.meetwork.profile.infra.http.response.ProfileResponse;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@Component
public class CommentResponse {

    @NotNull
    @NotEmpty
    String id;

    @NotNull
    ProfileResponse writer;

    @NotNull
    @NotEmpty
    String content;

    @NotNull
    LocalDateTime createAt;

    @NotNull
    LocalDateTime updateAt;

}
