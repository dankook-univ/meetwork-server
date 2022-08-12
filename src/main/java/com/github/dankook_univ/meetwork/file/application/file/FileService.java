package com.github.dankook_univ.meetwork.file.application.file;

import com.github.dankook_univ.meetwork.file.domain.File;
import com.github.dankook_univ.meetwork.file.domain.FileType;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    File upload(Profile uploader, FileType fileType, MultipartFile file);

    void delete(String fileId);
}
