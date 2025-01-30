package com.aptproject.springlibraryproject.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import static com.aptproject.springlibraryproject.library.constants.FileDirectoriesConstants.BOOKS_UPLOAD_DIRECTORY;

@Slf4j
public class FileHelper {

    private FileHelper() {
    }

    public static String createFile(final MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String resultFileName = "";
        try {
            Path path = Paths.get(BOOKS_UPLOAD_DIRECTORY + "/" + fileName).toAbsolutePath().normalize();
            if (!path.toFile().exists()) {
                Files.createDirectories(path);
            }
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            resultFileName = path.toString();

        } catch (IOException e) {
            log.error("FileHelper#createFile(): {}", e.getMessage());
        }
        return resultFileName;
    }
}
