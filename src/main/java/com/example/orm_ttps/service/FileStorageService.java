package com.example.orm_ttps.service;

import de.huxhorn.sulky.ulid.ULID;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public Resource getFile(String fileName) {
        try {
            Path path = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(path.toUri());
            if (!resource.exists()){
                return null;
            }
            if ( resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e){
            throw new RuntimeException("Error: " + e.getMessage());
        }

    }

    public String getContentType(String fileName) {
        try {
            Path path = Paths.get(uploadDir).resolve(fileName).normalize();
            return Files.probeContentType(path);
        } catch (IOException e) {
            return "application/octet-stream"; // Fallback type
        }
    }


    public boolean deleteFile(String fileName) {
        try {
            Path path = Paths.get(uploadDir).resolve(fileName).normalize();
            File file = path.toFile();
            if (file.exists()) {
                return file.delete(); // Returns true if the file was deleted successfully
            } else {
                return false; // File does not exist
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not delete the file: " + e.getMessage());
        }
    }


}
