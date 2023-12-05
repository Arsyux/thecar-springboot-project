package com.arsyux.thecar.domain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	
	// 업로드 경로
	private final String uploadPath = Paths.get("C:", "develop", "upload-files").toString();
		
	// 다중파일 업로드
	public List<FileVO> uploadFiles(final List<MultipartFile> multipartFiles) {
		List<FileVO> files = new ArrayList<>();
		for (MultipartFile multipartFile : multipartFiles) {
			if(multipartFile.isEmpty()) { continue; }
			
			files.add(uploadFile(multipartFile));
		}
		return files;
	}
	
	// 단일파일 업로드
	public FileVO uploadFile(final MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) { return null; }
		
        String saveName = generateSaveFilename(multipartFile.getOriginalFilename());
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")).toString();
        String uploadPath = getUploadPath(today) + File.separator + saveName;
        File uploadFile = new File(uploadPath);

        try {
            multipartFile.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return FileVO.builder()
                .original_name(multipartFile.getOriginalFilename())
                .save_name(saveName)
                .size(multipartFile.getSize())
                .build();
	}
	
	// 업로드 경로 반환
	private String getUploadPath(final String addPath) {
        return makeDirectories(uploadPath + File.separator + addPath);
    }
	
	// 업로드 폴더 생성
	private String makeDirectories(final String path) {
        File dir = new File(path);
        if (dir.exists() == false) {
            dir.mkdirs();
        }
        return dir.getPath();
    }
	
	// 저장 파일명 생성
	private String generateSaveFilename(final String filename) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = StringUtils.getFilenameExtension(filename);
        return uuid + "." + extension;
    }
}