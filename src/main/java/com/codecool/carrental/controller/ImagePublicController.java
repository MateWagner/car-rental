package com.codecool.carrental.controller;

import com.codecool.carrental.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImagePublicController {
    private final ImageService imageService;

    @GetMapping("{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Resource file = imageService.retrieveImage(filename);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
    }
}
