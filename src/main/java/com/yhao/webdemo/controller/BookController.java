package com.yhao.webdemo.controller;

import com.yhao.webdemo.controller.exception.SourceNotFoundException;
import com.yhao.webdemo.service.FileIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class BookController {

    private FileIOService fileIOService;

    @PostMapping("/upload")
    public boolean upload(@RequestParam("file") MultipartFile file) {
        try {
            fileIOService.saveFile(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam("path") String path) {
        byte[] fileBytes = fileIOService.getFile(path);
        if (fileBytes == null)
            throw new SourceNotFoundException();
        return new ResponseEntity<>(fileBytes, HttpStatus.OK);
    }

    @Autowired
    private void setFileIOService(FileIOService fileIOService) {
        this.fileIOService = fileIOService;
    }

}
