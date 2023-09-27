package com.codecool.carrental.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RandomFileNameGenerator {
    public String getRandomNameFromFileName(String filename) {
        String[] splitFilename = filename.split("[.]");
        return UUID.randomUUID() + "." + splitFilename[splitFilename.length - 1];
    }
}
