package com.evergreen.evergreenmedic.services;


import com.evergreen.evergreenmedic.dtos.entities.PostDto;
import com.evergreen.evergreenmedic.dtos.requests.post.CreatePostReqDto;
import com.evergreen.evergreenmedic.entities.PostEntity;
import com.evergreen.evergreenmedic.entities.UserEntity;
import com.evergreen.evergreenmedic.enums.MedicinePackage;
import com.evergreen.evergreenmedic.repositories.PostRepository;
import com.evergreen.evergreenmedic.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    //    @Mock
    private static MockedStatic<PostDto> postDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postDto = Mockito.mockStatic(PostDto.class);
    }

    @AfterEach
    void tearDown() {
        postDto.close();
    }


    private UserEntity getMockedUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setFirstName("First Name");
        userEntity.setLastName("Last Name");
        userEntity.setEmail("email@email.com");
        userEntity.setPassword("password");
        userEntity.setRole(UserRoleEnum.USER);
        userEntity.setPhoneNumber("1234567890");
        return userEntity;
    }

    private CreatePostReqDto getMockedPost() {
        CreatePostReqDto createPostReqDto = new CreatePostReqDto();
        createPostReqDto.setPostTitle("Post Title");
        createPostReqDto.setPostDescription("Post Description");
        createPostReqDto.setMedicineName("Panadol");
        createPostReqDto.setMedicinePackage(MedicinePackage.BLISTER);
        createPostReqDto.setMedicineQuantity((short) 30);
        createPostReqDto.setMedicineDosage("550MG");
        createPostReqDto.setNotes("Post notes");
        createPostReqDto.setContactNumber("03441500542");
        return createPostReqDto;
    }

    private PostEntity getMockedPostEntity() {
        PostEntity postEntity = new PostEntity();
        postEntity.setId(1);
        postEntity.setUser(getMockedUserEntity());
        return postEntity;
    }

    private PostDto getMockedPostDto() {
        PostDto postDto = new PostDto();
        postDto.setId(1);
        postDto.setPostTitle("Post Title");
        postDto.setPostDescription("Post Description");
        postDto.setMedicineName("Panadol");
        postDto.setMedicinePackage(MedicinePackage.BLISTER);
        postDto.setMedicineQuantity((short) 30);
        postDto.setMedicineDosage("550MG");
        postDto.setNotes("Post notes");
        postDto.setContactNumber("03441500542");
        //        postDto.setUserId(getMockedPostDto().getUserId());
        return postDto;
    }


    @Test
    void canCreatePostWithoutImages() throws IOException {
        UserEntity userEntity = getMockedUserEntity();
        CreatePostReqDto createPostReqDto = getMockedPost();
        PostEntity postEntity = getMockedPostEntity();

        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        when(postRepository.save(postEntity)).thenReturn(postEntity);

        PostDto postDto = getMockedPostDto();
        when(PostDto.entityToDto(postEntity)).thenReturn(postDto);

        PostDto result = postService.create(createPostReqDto, null, userEntity);
        System.out.println(result);

    }
}
