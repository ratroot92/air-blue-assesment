package com.evergreen.evergreenmedic.services;

import com.evergreen.evergreenmedic.dtos.entities.PostDto;
import com.evergreen.evergreenmedic.dtos.requests.post.CreatePostReqDto;
import com.evergreen.evergreenmedic.entities.PostEntity;
import com.evergreen.evergreenmedic.entities.StaticResourceEntity;
import com.evergreen.evergreenmedic.entities.UserEntity;
import com.evergreen.evergreenmedic.enums.MedicinePackage;
import com.evergreen.evergreenmedic.repositories.PostRepository;
import com.evergreen.evergreenmedic.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final FileUploadService fileUploadService;
    private final UserRepository userRepository;

    public PostDto create(CreatePostReqDto createPostReqDto, MultipartFile[] files, UserEntity userEntity) throws IOException {

        try {
            //
            String postTitle = createPostReqDto.getPostTitle();
            String postDescription = createPostReqDto.getPostDescription();
            String medicineName = createPostReqDto.getMedicineName();
            Short medicineQuantity = createPostReqDto.getMedicineQuantity();
            String medicineCompany = createPostReqDto.getMedicineCompany();
            String notes = createPostReqDto.getNotes();
            String contactNumber = createPostReqDto.getContactNumber();
            String medicineDosage = createPostReqDto.getMedicineDosage();
            MedicinePackage medicinePackage = createPostReqDto.getMedicinePackage();
            //
            PostEntity postEntity = new PostEntity();
            postEntity.setPostTitle(postTitle);
            postEntity.setPostDescription(postDescription);
            postEntity.setMedicineName(medicineName);
            postEntity.setMedicineCompany(medicineCompany);
            postEntity.setMedicineQuantity(medicineQuantity);
            postEntity.setNotes(notes);
            postEntity.setContactNumber(contactNumber);
            postEntity.setMedicineDosage(medicineDosage);
            postEntity.setMedicinePackage(medicinePackage);
            userEntity = userRepository.findById(userEntity.getId()).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
            postEntity.setUser(userEntity);
            postEntity = postRepository.save(postEntity);
            //
            if (files != null) {
                List<StaticResourceEntity> staticResourceEntities = new ArrayList<>();
                for (MultipartFile file : files) {
                    staticResourceEntities.add(fileUploadService.uploadSimpleImageStaticFolder(file));

                }
                postEntity = postRepository.findById(postEntity.getId()).orElseThrow(() -> new EntityNotFoundException("Post not found"));
                postEntity.setFiles(staticResourceEntities);
                postEntity = postRepository.save(postEntity);


            }
            return PostDto.entityToDto(postEntity);
        } catch (Exception ex) {
            ex.printStackTrace();  // For full stack trace in console
            log.error("Error in create method", ex);
            throw new IOException(ex);
        }
    }

    public List<PostDto> getAllPosts() throws IOException {
        List<PostEntity> postEntities = postRepository.findAll();
        List<PostDto> postDtos = new ArrayList<>();
        for (PostEntity postEntity : postEntities) {
            postDtos.add(PostDto.entityToDto(postEntity));

        }
        return postDtos;
    }

    public List<PostDto> getAllPostsByUser(UserEntity userEntity) throws IOException {
        List<PostEntity> postEntities = postRepository.findPostEntitiesByUser(userEntity);
        List<PostDto> postDtos = new ArrayList<>();
        for (PostEntity postEntity : postEntities) {
            postDtos.add(PostDto.entityToDto(postEntity));

        }
        return postDtos;
    }

    public PostDto getPostById(Integer postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        return PostDto.entityToDto(postEntity);
    }


}
