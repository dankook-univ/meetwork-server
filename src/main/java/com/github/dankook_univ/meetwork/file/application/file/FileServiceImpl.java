package com.github.dankook_univ.meetwork.file.application.file;


import com.github.dankook_univ.meetwork.file.application.storage.StorageServiceImpl;
import com.github.dankook_univ.meetwork.file.domain.File;
import com.github.dankook_univ.meetwork.file.domain.FileType;
import com.github.dankook_univ.meetwork.file.exceptions.FailedToFileUploadException;
import com.github.dankook_univ.meetwork.file.exceptions.NotSupportedFileFormatException;
import com.github.dankook_univ.meetwork.file.infra.persistence.FileRepositoryImpl;
import com.github.dankook_univ.meetwork.member.application.MemberServiceImpl;
import java.io.IOException;
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
    private final FileRepositoryImpl fileRepository;
    private final StorageServiceImpl storageService;
    private final MemberServiceImpl memberService;

    @Override
    @Transactional
    public File upload(String memberId, FileType fileType, MultipartFile multipartFile) {
        try {
            String tikaChecked = tika.detect(multipartFile.getInputStream());
            System.out.println("checked mime : " + tikaChecked);
        } catch (IOException e) {
            throw new FailedToFileUploadException();
        }

        String mime = multipartFile.getOriginalFilename() == null
            ? "jpeg"
            : multipartFile.getOriginalFilename().substring(
                multipartFile.getOriginalFilename().lastIndexOf(".") + 1
            ).toLowerCase();
        if (
            !"gif".equals(mime)
                && !"jpg".equals(mime)
                && !"png".equals(mime)
                && !"jpeg".equals(mime)
        ) {
            throw new NotSupportedFileFormatException(mime);
        }

        File file = fileRepository.save(
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

        try {
            storageService.upload(multipartFile, file.getKey());
            return file;
        } catch (FailedToFileUploadException e) {
            fileRepository.delete(file.getId().toString());
            return null;
        }
    }

    @Override
    @Transactional
    public void delete(String fileId) {
        fileRepository.getById(fileId).ifPresent(file -> storageService.delete(file.getKey()));
        fileRepository.delete(fileId);
    }

    @Override
    @Transactional
    public void deleteByUploaderId(String uploaderId) {
        List<File> list = fileRepository.getByUploaderId(uploaderId);
        for (File file : list) {
            storageService.delete(file.getKey());
            fileRepository.delete(file);
        }
    }
}
