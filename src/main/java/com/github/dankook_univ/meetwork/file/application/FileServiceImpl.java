package com.github.dankook_univ.meetwork.file.application;


import com.github.dankook_univ.meetwork.file.domain.File;
import com.github.dankook_univ.meetwork.file.domain.FileType;
import com.github.dankook_univ.meetwork.file.infra.minio.MinioService;
import com.github.dankook_univ.meetwork.file.infra.persistence.FileRepositoryImpl;
import com.github.dankook_univ.meetwork.member.domain.Member;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	final private FileRepositoryImpl fileRepository;

	final private MinioService minioService;


	@Override
	public File upload(Member uploader, FileType fileType, MultipartFile file) {
		File entity = fileRepository.save(File.builder()
			.uploader(uploader)
			.type(fileType)
			.mime(
				file.getOriginalFilename() == null
					? "jpeg"
					: file.getOriginalFilename().substring(
						file.getOriginalFilename().lastIndexOf(".") + 1
					)
			)
			.name(file.getOriginalFilename())
			.build()
		);

		try {
			InputStream inputStream = file.getInputStream();
			if (!minioService.upload(entity.getKey(), inputStream, file.getSize(),
				entity.getMime())) {
				entity = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			entity = null;
		}
		return entity;
	}

	@Override
	public void delete(UUID fileId) {
		fileRepository.getById(fileId).ifPresent(file -> minioService.delete(file.getKey()));
		fileRepository.delete(fileId);
	}
}
