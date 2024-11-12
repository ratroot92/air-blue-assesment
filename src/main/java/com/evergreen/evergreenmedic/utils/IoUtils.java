package com.evergreen.evergreenmedic.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

@Component
@Slf4j
public class IoUtils {


    public void deleteFileFromClasspath(String filePath) throws IOException {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource("static/" + filePath);
            log.info("deleting file {}", resource.getPath());
            if (resource == null) {
                throw new FileNotFoundException(filePath);
            } else {
                File file = new File(resource.getFile());
                if (file.exists()) {
                    file.delete();
                    log.info("file delete successfully");
                } else {
                    log.info("file does not exist");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void deleteFileFromProjectPath(String filePath) throws IOException {
        try {
            String projectRoot = System.getProperty("user.dir");
            String staticFolderPath = projectRoot + "/static/" + filePath;
            File file = new File(staticFolderPath);
            log.info("Deleting file from path: {}", file.getAbsolutePath());
            if (file.exists()) {
                if (file.delete()) {
                    log.info("File deleted successfully");
                } else {
                    log.info("Failed to delete file");
                }
            } else {
                log.info("File does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }


}
