package com.evergreen.evergreenmedic.services;


import com.evergreen.evergreenmedic.entities.StaticResourceEntity;
import com.evergreen.evergreenmedic.repositories.StaticResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@Slf4j
public class FileUploadService {
    private final static int allowedUploadSize = 1000000 * 5; // 5 MBs
    private static final List<String> allowedMimeTypes = List.of("image/jpeg", "image/png", "image/gif"); // 5 MBs
    private StaticResourceRepository staticResourceRepository;

    public FileUploadService(StaticResourceRepository staticResourceRepository) {
        this.staticResourceRepository = staticResourceRepository;
    }


    public StaticResourceEntity uploadSimpleImageStaticFolder(MultipartFile multipartFile) throws IOException {

        if (multipartFile.getSize() > allowedUploadSize) {
            throw new BadRequestException("Please upload a profile image with size less than " + allowedUploadSize + " bytes");
        }
        if (!allowedMimeTypes.stream().filter(mimeType -> mimeType.equalsIgnoreCase(multipartFile.getContentType())).findFirst().isPresent()) {
            throw new BadRequestException("mime-type '" + multipartFile.getContentType() + "' not supported");
        }
        final String fileExtension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        final String uploadDir = "static/uploads/user/profile-images/";
        final String fileName = "user_profile_image" + "_" + System.currentTimeMillis() + fileExtension;

        ensureDirectoryExists(uploadDir);
        Path uploadDirectory = Paths.get(uploadDir);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path path = uploadDirectory.resolve(fileName);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            StaticResourceEntity staticResource = new StaticResourceEntity();
            staticResource.setSize(String.valueOf(multipartFile.getSize()));
            staticResource.setName(multipartFile.getOriginalFilename());
            staticResource.setContentType(multipartFile.getContentType().toString());
            staticResource.setPath(path.toString().replaceAll("static/", ""));
            staticResource = staticResourceRepository.save(staticResource);
            return staticResource;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Failed to save file: " + multipartFile.getOriginalFilename());
        }

    }

    public StaticResourceEntity uploadSingleImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile.getSize() > allowedUploadSize) {
            throw new BadRequestException("Please upload a profile image with size less than " + allowedUploadSize + " bytes");
        }
        final String uploadDir = "src/main/resources/static/uploads/user/profile-images/";
        final String fileName = "user_profile_image" + "_" + System.currentTimeMillis();
        final String fileExtension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        if (!allowedMimeTypes.stream().filter(mimeType -> mimeType.equalsIgnoreCase(multipartFile.getContentType())).findFirst().isPresent()) {
            throw new BadRequestException("mime-type '" + multipartFile.getContentType() + "' not supported");
        }
        File file = new File(uploadDir + fileName + fileExtension);
        ensureDirectoryExists(uploadDir);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
        StaticResourceEntity staticResource = new StaticResourceEntity();
        staticResource.setSize(String.valueOf(multipartFile.getSize()));
        staticResource.setName(multipartFile.getOriginalFilename());
        staticResource.setContentType(multipartFile.getContentType().toString());
        staticResource.setPath(file.getPath().replaceAll("src/main/resources/static/", ""));
        staticResource = staticResourceRepository.save(staticResource);
        return staticResource;
    }

    protected void ensureDirectoryExists(String uploadDir) {
        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                boolean dirsCreated = dir.mkdirs();
                if (!dirsCreated) {
                    throw new IOException("Failed to create directory: " + uploadDir);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
