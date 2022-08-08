package com.github.dankook_univ.meetwork.file.application.file;

import com.github.dankook_univ.meetwork.file.domain.File;
import com.github.dankook_univ.meetwork.file.domain.FileType;
import com.github.dankook_univ.meetwork.member.domain.Member;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    File upload(Member uploader, FileType fileType, MultipartFile file);

    void delete(UUID fileId);
}
