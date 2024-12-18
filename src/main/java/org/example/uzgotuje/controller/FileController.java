package org.example.uzgotuje.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class FileController {

    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            String sambaBaseUrl = "smb://192.168.0.188/shared/";
            String filePath = sambaBaseUrl + filename;
            SmbFile smbFile = new SmbFile(filePath);

            if (smbFile.exists() && smbFile.isFile()) {
                System.out.println("File exists: " + smbFile.getName());
                InputStream inputStream = new SmbFileInputStream(smbFile);
                Resource resource = new InputStreamResource(inputStream);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
