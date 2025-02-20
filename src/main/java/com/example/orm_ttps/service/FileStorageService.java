package com.example.orm_ttps.service;

import de.huxhorn.sulky.ulid.ULID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ULID ulid = new ULID();

    public String storeFile(MultipartFile file) throws IOException {
        if (file == null | file.isEmpty()) {
            return null;
        }
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = ulid.nextULID() + "_" + file.getOriginalFilename();

        Path path = Path.of(uploadDir , fileName);

        Files.copy(file.getInputStream(), path);

        return fileName;







    }


}
