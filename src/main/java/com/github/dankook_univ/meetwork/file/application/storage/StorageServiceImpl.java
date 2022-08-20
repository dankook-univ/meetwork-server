package com.github.dankook_univ.meetwork.file.application.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.github.dankook_univ.meetwork.file.exceptions.FailedToFileDeleteException;
import com.github.dankook_univ.meetwork.file.exceptions.FailedToFileUploadException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final AmazonS3 storage;

    @Value("storage.bucket")
    private String bucket;

    public void upload(MultipartFile file, String key) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType("application/x-directory");

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                bucket,
                key,
                new ByteArrayInputStream(file.getBytes()),
                objectMetadata
            );

            storage.putObject(putObjectRequest);
            setAccessLevel(key);
        } catch (AmazonS3Exception | IOException e) {
            log.error("[StorageService Exception] 파일 업로드 오류: {}", e.getMessage());
            throw new FailedToFileUploadException();
        }
    }

    @Override
    public void delete(String key) {
        try {
            storage.deleteObject(bucket, key);
        } catch (AmazonS3Exception e) {
            log.error("[StorageService Exception] 파일 삭제 오류: {}", e.getMessage());
            throw new FailedToFileDeleteException();
        }
    }

    private void setAccessLevel(String key) {
        AccessControlList accessControlList = storage.getObjectAcl(bucket, key);
        accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        storage.setObjectAcl(bucket, key, accessControlList);
    }
}
