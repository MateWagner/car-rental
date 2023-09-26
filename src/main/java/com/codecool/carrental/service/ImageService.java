package com.codecool.carrental.service;

import com.codecool.carrental.controller.dto.ImageListDTO;
import com.codecool.carrental.controller.dto.NewPictureDTO;
import com.codecool.carrental.repository.ImageDao;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final static String IMAGE_URL_PREFIX = "images";
    private final ImageDao imageDao;

    public ImageListDTO getAllImagesUrls() {
        List<String> imgUrls = imageDao.getImagesNames().stream()
                .map(this::getImageURL)
                .toList();
        return new ImageListDTO(imgUrls);
    }

    public NewPictureDTO storeImageGetURL(MultipartFile file) {
        String filename = imageDao.storeImage(file);
        return new NewPictureDTO(getImageURL(filename));
    }

    public Resource retrieveImage(String filename) {
        return imageDao.loadImage(filename);
    }

    private String getImageURL(String filename) {
        return "/" + IMAGE_URL_PREFIX + "/" + filename;
    }
}
