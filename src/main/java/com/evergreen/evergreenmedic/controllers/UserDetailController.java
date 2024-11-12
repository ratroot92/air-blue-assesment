package com.evergreen.evergreenmedic.controllers;

import com.evergreen.evergreenmedic.dtos.ProtectedUserDto;
import com.evergreen.evergreenmedic.dtos.requests.user_detail.CreateUserAddressReqDto;
import com.evergreen.evergreenmedic.dtos.requests.user_detail.PartialUpdateUserAddressReqDto;
import com.evergreen.evergreenmedic.entities.UserAddressEntity;
import com.evergreen.evergreenmedic.services.UserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("api/v1/user-details")
@Slf4j
public class UserDetailController {
    private final UserDetailService userDetailService;

    public UserDetailController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @PostMapping(value = "/profile-image", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProtectedUserDto> uploadUserProfileImage(@RequestParam(name = "file", required = true) MultipartFile file, HttpServletRequest request) throws IOException {
        if (request instanceof MultipartHttpServletRequest multipartRequest) {
            List<MultipartFile> files = multipartRequest.getFiles("file");
            if (files.size() > 1) {
                throw new BadRequestException("Only one file is allowed.");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDetailService.uploadProfileImage(file));
    }

    @PostMapping(value = "/cover-image", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProtectedUserDto> uploadUserCoverImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if (request instanceof MultipartHttpServletRequest multipartRequest) {
            List<MultipartFile> files = multipartRequest.getFiles("file");
            if (files.size() > 1) {
                throw new BadRequestException("Only one file is allowed.");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDetailService.uploadCoverImage(file));
    }

    @PostMapping(value = "/thumbnail", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProtectedUserDto> uploadUserThumbnail(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if (request instanceof MultipartHttpServletRequest multipartRequest) {
            List<MultipartFile> files = multipartRequest.getFiles("file");
            if (files.size() > 1) {
                throw new BadRequestException("Only one file is allowed.");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDetailService.uploadThumbnail(file));
    }

    @PostMapping(value = "/address")
    public ResponseEntity<ProtectedUserDto> createUserAddress(@RequestBody @Valid CreateUserAddressReqDto createUserAddressReqDto, HttpServletRequest request) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userDetailService.createUserAddress(createUserAddressReqDto));
    }

    @DeleteMapping(value = "/address/{id}")
    public ResponseEntity<UUID> deleteUserAddressById(@PathVariable("id") UUID addressId, HttpServletRequest request) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userDetailService.deleteUserAddressById(addressId));
    }

    @PatchMapping(value = "/address")
    public ResponseEntity<UserAddressEntity> partiallyUpdateAddressById(@RequestBody @Valid PartialUpdateUserAddressReqDto partialUpdateUserAddressReqDto, HttpServletRequest request) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userDetailService.partiallyUpdateAddress(partialUpdateUserAddressReqDto));
    }

}
