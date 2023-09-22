package com.codecool.carrental.controller;

import com.codecool.carrental.controller.dto.ImageListDTO;
import com.codecool.carrental.controller.dto.NewPictureDTO;
import com.codecool.carrental.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ImageAdminController {
    private final ImageService imageService;

    @GetMapping("images")
    public ImageListDTO getImagesUrlList() {
        return imageService.getAllImagesUrls();
    }

    @PostMapping("images")
    public NewPictureDTO handleFileUpload(@RequestParam MultipartFile file) {
        return imageService.storeImageGetURL(file);
    }
}
