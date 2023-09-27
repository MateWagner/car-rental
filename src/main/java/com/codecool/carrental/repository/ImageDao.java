package com.codecool.carrental.repository;

import com.codecool.carrental.exception.BadRequestException;
import com.codecool.carrental.exception.NotFoundException;
import com.codecool.carrental.utils.ImageProperties;
import com.codecool.carrental.utils.RandomFileNameGenerator;
import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ImageDao {
    private final RandomFileNameGenerator fileNameGenerator;
    private final ImageProperties imageProperties;


    public List<String> getImagesNames() {
        try (Stream<Path> files = Files.list(getImageFolderPath())) {
            return files.map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new NotFoundException("Image folder empty");
        }
    }

    public Resource loadImage(String filename) {
        try {
            Path file = getImageFolderPath().resolve(filename);
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
            String fileName = fileNameGenerator.getRandomNameFromFileName(file.getOriginalFilename());

            Path destinationFile = getImageFolderPath().resolve(Paths.get(fileName))
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

    private Path getImageFolderPath() {
        return Paths.get(imageProperties.getPath());
    }
}
