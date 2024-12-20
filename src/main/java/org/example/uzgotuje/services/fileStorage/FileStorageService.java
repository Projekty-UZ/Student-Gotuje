package org.example.uzgotuje.services.fileStorage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    private final String networkUploadDir = "\\\\192.168.1.1\\shared\\";

    public String saveFile(MultipartFile file) {
        try {
            // Generate a unique filename to avoid collisions
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Construct the path on the remote device
            Path networkPath = Paths.get(networkUploadDir + fileName);

            // Save file to the network directory
            Files.copy(file.getInputStream(), networkPath);

            // Return the relative path of the file (e.g., "\\192.168.1.1\shared_folder\filename.jpg")
            return networkPath.toString();
        }
        catch (IOException e) {
            return "Failed to save file";
        }
    }

    public void deleteFile(String path) throws IOException {
        // Construct the path on the remote device
        Path networkPath = Paths.get(path);

        // Delete the file from the network directory
        Files.delete(networkPath);
    }
}
