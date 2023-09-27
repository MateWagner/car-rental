package com.codecool.carrental.repository;

import com.codecool.carrental.exception.BadRequestException;
import com.codecool.carrental.exception.NotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ImageDao {
    private final Path imageFolderPath;

    public ImageDao() {
        this.imageFolderPath = getImagesFolderPath();
    }

    public List<String> getImagesNames() {
        try (Stream<Path> files = Files.list(imageFolderPath)) {
            return files.map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new NotFoundException("Image folder empty");
        }
    }

    public Resource loadImage(String filename) {
        try {
            Path file = imageFolderPath.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new NotFoundException("Can't find image by name: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public String storeImage(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new BadRequestException("Failed to store empty file.");
            }
            String fileName = getNewFileName(file.getOriginalFilename());

            Path destinationFile = this.imageFolderPath.resolve(Paths.get(fileName))
                    .normalize().toAbsolutePath();
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                return fileName;
            }
        } catch (IOException e) {
            throw new BadRequestException("Failed to store file.");
        }
    }

    private String getNewFileName(String originalFileName) {
        String[] fileExtension = originalFileName.split("[.]");
        return UUID.randomUUID() + "." + fileExtension[fileExtension.length - 1];
    }

    private static Path getImagesFolderPath() {
        Path currentWorkingDir = Paths.get(System.getProperty("user.dir"));
        return currentWorkingDir.resolve("images"); // TODO maybe env?
    }

}
