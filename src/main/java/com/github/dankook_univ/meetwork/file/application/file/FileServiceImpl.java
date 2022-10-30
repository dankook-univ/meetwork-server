package com.github.dankook_univ.meetwork.file.application.file;


import com.github.dankook_univ.meetwork.file.application.storage.StorageServiceImpl;
import com.github.dankook_univ.meetwork.file.domain.File;
import com.github.dankook_univ.meetwork.file.domain.FileType;
import com.github.dankook_univ.meetwork.file.exceptions.FailedToFileUploadException;
import com.github.dankook_univ.meetwork.file.exceptions.NotSupportedFileFormatException;
import com.github.dankook_univ.meetwork.file.infra.persistence.FileRepositoryImpl;
import com.github.dankook_univ.meetwork.member.application.MemberServiceImpl;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {

    private static final Tika tika = new Tika();
    private static final List<String> FILETYPE = Arrays.asList(
        "image/jpeg",
        "image/png",
        "image/jpg"
    );
    private final FileRepositoryImpl fileRepository;
    private final StorageServiceImpl storageService;
    private final MemberServiceImpl memberService;

    @Override
    @Transactional
    public File upload(Long memberId, FileType fileType, MultipartFile multipartFile) {
        File file = null;
        try {
            String mime = tika.detect(multipartFile.getInputStream());
            if (!FILETYPE.contains(mime)) {
                throw new NotSupportedFileFormatException(mime);
            }
            mime = mime.substring(mime.lastIndexOf("/") + 1).toLowerCase();

            file = fileRepository.save(
                File.builder()
                    .uploader(memberService.getById(memberId))
                    .type(fileType)
                    .mime(mime)
                    .name(
                        Objects.requireNonNull(multipartFile.getOriginalFilename())
                            .replaceAll("\\.", "")
                            .replaceAll("/", "")
                            .replaceAll("\\\\", "")
                    )
                    .build()
            );

            storageService.upload(multipartFile, file.getKey());
        } catch (IOException e) {
            throw new FailedToFileUploadException();
        } catch (FailedToFileUploadException e) {
            if (file != null) {
                fileRepository.delete(file.getId());
            }
        }

        return file;
    }

    @Override
    @Transactional
    public void delete(Long fileId) {
        fileRepository.getById(fileId).ifPresent((file) -> storageService.delete(file.getKey()));
        fileRepository.delete(fileId);
    }

    @Override
    @Transactional
    public void deleteByUploaderId(Long uploaderId) {
        fileRepository.getByUploaderId(uploaderId)
            .forEach(
                file -> {
                    storageService.delete(file.getKey());
                    fileRepository.delete(file);
                }
            );
    }
}
