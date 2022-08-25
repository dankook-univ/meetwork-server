package com.github.dankook_univ.meetwork.file.infra.http;

import com.github.dankook_univ.meetwork.file.application.file.FileServiceImpl;
import com.github.dankook_univ.meetwork.file.domain.FileType;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileServiceImpl fileService;

    @ApiOperation(value = "파일 업로드", notes = "파일 한 개를 업로드 할 수 있어요.")
    @PostMapping("/upload")
    public ResponseEntity<String> upload(
        @ApiIgnore Authentication authentication,
        @Valid @NotNull MultipartFile image
    ) {
        return ResponseEntity.ok().body(
            fileService.upload(authentication.getName(), FileType.post, image).toResponse().getUrl()
        );
    }

    @ApiOperation(value = "파일들 업로드", notes = "파일 여러개를 업로드 할 수 있어요.")
    @PostMapping("/uploads")
    public ResponseEntity<List<String>> uploads(
        @ApiIgnore Authentication authentication,
        @Valid @NotNull List<MultipartFile> images
    ) {
        return ResponseEntity.ok().body(
            images.stream().map(
                (file) -> fileService.upload(authentication.getName(), FileType.post, file)
                    .toResponse()
                    .getUrl()
            ).collect(Collectors.toList())
        );
    }
}
