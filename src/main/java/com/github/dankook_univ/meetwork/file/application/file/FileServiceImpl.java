package com.github.dankook_univ.meetwork.file.application.file;


import com.github.dankook_univ.meetwork.file.application.minio.MinioServiceImpl;
import com.github.dankook_univ.meetwork.file.domain.File;
import com.github.dankook_univ.meetwork.file.domain.FileType;
import com.github.dankook_univ.meetwork.file.exceptions.NotSupportedFileFormatException;
import com.github.dankook_univ.meetwork.file.infra.persistence.FileRepositoryImpl;
import com.github.dankook_univ.meetwork.member.application.MemberServiceImpl;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepositoryImpl fileRepository;

    private final MinioServiceImpl minioService;

    private final MemberServiceImpl memberService;

    @Override
    public File upload(String memberId, FileType fileType, MultipartFile multipartFile) {
        String mime = multipartFile.getOriginalFilename() == null
            ? "jpeg"
            : multipartFile.getOriginalFilename().substring(
                multipartFile.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        if (!"gif".equals(mime) && !"jpg".equals(mime) && !"png".equals(mime) && !"jpeg".equals(
            mime)) {
            throw new NotSupportedFileFormatException();
        }

        File file = fileRepository.save(
            File.builder()
                .uploader(memberService.getById(memberId))
                .type(fileType)
                .mime(mime)
                .name(multipartFile.getOriginalFilename()
                    .replaceAll("￦￦.", "")
                    .replaceAll("/", "")
                    .replaceAll("￦￦￦￦", ""))
                .build()
        );

        try {
            InputStream inputStream = multipartFile.getInputStream();
            if (!minioService.upload(file.getKey(), inputStream, multipartFile.getSize(),
                file.getMime())) {
                file = null;
            }
        } catch (Exception e) {
            file = null;
        }
        return file;
    }

    @Override
    public void delete(String fileId) {
        fileRepository.getById(fileId).ifPresent(file -> minioService.delete(file.getKey()));
        fileRepository.delete(fileId);
    }
}
