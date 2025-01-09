package com.evergreen.evergreenmedic.controllers;


import com.evergreen.evergreenmedic.dtos.entities.PostDto;
import com.evergreen.evergreenmedic.dtos.requests.post.CreatePostReqDto;
import com.evergreen.evergreenmedic.entities.UserEntity;
import com.evergreen.evergreenmedic.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("api/v1/post")
@AllArgsConstructor
public class PostController {
    private PostService postService;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDto> create(@ModelAttribute CreatePostReqDto createPostReqDto,
                                          @RequestParam(name = "files", required = false) MultipartFile[] files, @RequestAttribute("user") UserEntity userEntity) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(createPostReqDto, files, userEntity));
    }

    @GetMapping("all")
    public ResponseEntity<List<PostDto>> getAllPosts() throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id", required = true) Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id));


    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPostsByUser(@RequestAttribute("user") UserEntity userEntity) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPostsByUser(userEntity));
    }


}
