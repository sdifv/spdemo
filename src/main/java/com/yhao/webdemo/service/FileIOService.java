package com.yhao.webdemo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileIOService {
    private static final String fileContainer = Paths.get(System.getProperty("user.dir"), "data").toString();

    static {
        File file = new File(fileContainer);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public void saveFile(MultipartFile file) throws IOException {
        Path path = Paths.get(fileContainer, file.getOriginalFilename());
        file.transferTo(path);
    }

    public byte[] getFile(String path) {
        Path filePath = Paths.get(fileContainer, path);
        try {
            FileInputStream in = new FileInputStream(filePath.toFile());
            byte[] data = new byte[in.available()];
            in.read(data);
            in.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
