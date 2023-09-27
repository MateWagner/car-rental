package com.codecool.carrental.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Component
@ConfigurationProperties("images")
public class ImageProperties {
    private String dir = "images";
    private String path = "/";

    public String getPath() {
        if (Objects.equals(path, "DOCKER"))
            return getDockerVolumeLocation();
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String getDockerVolumeLocation() {
        Path currentWorkingDir = Paths.get(System.getProperty("user.dir"));
        return currentWorkingDir.resolve(dir).toString();
    }
}
