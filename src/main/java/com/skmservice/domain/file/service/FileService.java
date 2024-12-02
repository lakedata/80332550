package com.skmservice.domain.file.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private static final String UPLOAD_DIR = "/uploads";  // 파일을 저장할 디렉토리 경로

    // 파일 저장 메서드
    public String storeFile(MultipartFile file) {
        // 파일 이름을 UUID로 변경하여 충돌 방지
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // 실제 파일을 저장할 경로
        Path filePath = Paths.get(UPLOAD_DIR, fileName);

        try {
            // 디렉토리가 없으면 생성
            Files.createDirectories(filePath.getParent());

            // 파일 저장
            file.transferTo(filePath.toFile());

            // 저장된 파일의 경로 반환 (DB에 저장할 경로)
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }
}
