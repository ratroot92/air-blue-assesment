package com.evergreen.evergreenmedic.services;


import com.evergreen.evergreenmedic.dtos.ProtectedUserDto;
import com.evergreen.evergreenmedic.dtos.requests.user_detail.CreateUserAddressReqDto;
import com.evergreen.evergreenmedic.dtos.requests.user_detail.PartialUpdateUserAddressReqDto;
import com.evergreen.evergreenmedic.entities.StaticResourceEntity;
import com.evergreen.evergreenmedic.entities.UserAddressEntity;
import com.evergreen.evergreenmedic.entities.UserDetailEntity;
import com.evergreen.evergreenmedic.entities.UserEntity;
import com.evergreen.evergreenmedic.repositories.StaticResourceRepository;
import com.evergreen.evergreenmedic.repositories.UserAddressRepository;
import com.evergreen.evergreenmedic.repositories.UserDetailRepository;
import com.evergreen.evergreenmedic.repositories.UserRepository;
import com.evergreen.evergreenmedic.utils.IoUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserDetailService {
    protected final StaticResourceRepository staticResourceRepository;
    protected final FileUploadService fileUploadService;
    protected final UserDetailRepository userDetailRepository;
    protected final UserRepository userRepository;
    protected final UserAddressRepository userAddressRepository;
    protected final IoUtils ioUtils;


    public UserDetailService(FileUploadService fileUploadService, UserDetailRepository userDetailRepository, UserRepository userRepository, StaticResourceRepository staticResourceRepository, IoUtils ioUtils, UserAddressRepository userAddressRepository) {
        this.fileUploadService = fileUploadService;
        this.userDetailRepository = userDetailRepository;
        this.userRepository = userRepository;
        this.staticResourceRepository = staticResourceRepository;
        this.userAddressRepository = userAddressRepository;
        this.ioUtils = ioUtils;
    }

    public ProtectedUserDto uploadProfileImage(MultipartFile profileImage) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getPrincipal().toString();
        UserEntity userEntity = userRepository.findByEmail(userEmail);
        UserDetailEntity userDetailEntity = userEntity.getUserDetailEntity();
        if (userDetailEntity == null) {
            throw new BadRequestException("UserDetailEntity is null.");
        }
        if (userEntity.getUserDetailEntity().getProfileImage() == null) {
            StaticResourceEntity staticResource = fileUploadService.uploadSimpleImageStaticFolder(profileImage);
            userEntity.getUserDetailEntity().setProfileImage(staticResource);
            userRepository.save(userEntity);
            log.info("Profile image uploaded successfully");
        } else {
            System.out.println(userEntity.getUserDetailEntity().getProfileImage());
//            Delete file locally on server
            ioUtils.deleteFileFromProjectPath(userEntity.getUserDetailEntity().getProfileImage().getPath());
            StaticResourceEntity existingResource = userDetailEntity.getProfileImage();
//            Remove reference first ,update to db then delete static resource
            userDetailEntity.setProfileImage(null);
            userDetailEntity = userDetailRepository.save(userDetailEntity);
            staticResourceRepository.delete(existingResource);
//            save new file
            StaticResourceEntity staticResource = fileUploadService.uploadSimpleImageStaticFolder(profileImage);
            userDetailEntity.setProfileImage(staticResource);
            userDetailRepository.save(userDetailEntity);
            log.info("Profile image updated successfully");

        }
        return ProtectedUserDto.mapToDto(userEntity);
    }

    public ProtectedUserDto uploadCoverImage(MultipartFile coverImage) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getPrincipal().toString();
        UserEntity userEntity = userRepository.findByEmail(userEmail);
        UserDetailEntity userDetailEntity = userEntity.getUserDetailEntity();
        if (userDetailEntity == null) {
            throw new BadRequestException("UserDetailEntity is null.");
        }
        if (userEntity.getUserDetailEntity().getCoverImage() == null) {
            StaticResourceEntity staticResource = fileUploadService.uploadSimpleImageStaticFolder(coverImage);
            userEntity.getUserDetailEntity().setCoverImage(staticResource);
            userRepository.save(userEntity);
            log.info("Cover image uploaded successfully");
        } else {
            System.out.println(userEntity.getUserDetailEntity().getCoverImage());
//            Delete file locally on server
            ioUtils.deleteFileFromProjectPath(userEntity.getUserDetailEntity().getCoverImage().getPath());
            StaticResourceEntity existingResource = userDetailEntity.getCoverImage();
//            Remove reference first ,update to db then delete static resource
            userDetailEntity.setCoverImage(null);
            userDetailEntity = userDetailRepository.save(userDetailEntity);
            staticResourceRepository.delete(existingResource);
//            save new file
            StaticResourceEntity staticResource = fileUploadService.uploadSimpleImageStaticFolder(coverImage);
            userDetailEntity.setCoverImage(staticResource);
            userDetailRepository.save(userDetailEntity);
            log.info("Cover image updated successfully");

        }
        return ProtectedUserDto.mapToDto(userEntity);
    }

    public ProtectedUserDto uploadThumbnail(MultipartFile thumbnail) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getPrincipal().toString();
        UserEntity userEntity = userRepository.findByEmail(userEmail);
        UserDetailEntity userDetailEntity = userEntity.getUserDetailEntity();
        if (userDetailEntity == null) {
            throw new BadRequestException("UserDetailEntity is null.");
        }
        if (userEntity.getUserDetailEntity().getThumbnailImage() == null) {
            StaticResourceEntity staticResource = fileUploadService.uploadSimpleImageStaticFolder(thumbnail);
            userEntity.getUserDetailEntity().setThumbnailImage(staticResource);
            userRepository.save(userEntity);
            log.info("Thumbnail image uploaded successfully");
        } else {
            System.out.println(userEntity.getUserDetailEntity().getThumbnailImage());
//            Delete file locally on server
            ioUtils.deleteFileFromProjectPath(userEntity.getUserDetailEntity().getThumbnailImage().getPath());
            StaticResourceEntity existingResource = userDetailEntity.getThumbnailImage();
//            Remove reference first ,update to db then delete static resource
            userDetailEntity.setThumbnailImage(null);
            userDetailEntity = userDetailRepository.save(userDetailEntity);
            staticResourceRepository.delete(existingResource);
//            save new file
            StaticResourceEntity staticResource = fileUploadService.uploadSimpleImageStaticFolder(thumbnail);
            userDetailEntity.setThumbnailImage(staticResource);
            userDetailRepository.save(userDetailEntity);
            log.info("Thumbnail image updated successfully");

        }
        return ProtectedUserDto.mapToDto(userEntity);
    }

    public ProtectedUserDto createUserAddress(CreateUserAddressReqDto createUserAddressReqDto) throws BadRequestException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getPrincipal().toString();
        UserEntity userEntity = userRepository.findByEmail(userEmail);
        UserDetailEntity userDetailEntity = userEntity.getUserDetailEntity();
        if (userDetailEntity == null) {
            throw new BadRequestException("User details does not exists");
        }
        UserAddressEntity userAddressEntity = new UserAddressEntity();
        userAddressEntity.setCountry(createUserAddressReqDto.getCountry());
        userAddressEntity.setCity(createUserAddressReqDto.getCity());
        userAddressEntity.setStreet(createUserAddressReqDto.getStreet());
        userAddressEntity.setPostalCode(createUserAddressReqDto.getPostalCode());
        userAddressEntity.setHouse(createUserAddressReqDto.getHouse());
        userAddressEntity.setDescriptiveAddress(createUserAddressReqDto.getDescriptiveAddress());
        userAddressEntity.setPrimaryPhoneNumber(createUserAddressReqDto.getPrimaryPhoneNumber());
        userAddressEntity.setSecondaryPhoneNumber(createUserAddressReqDto.getSecondaryPhoneNumber());
        userAddressEntity.setUserDetailEntity(userEntity.getUserDetailEntity());
        userAddressRepository.save(userAddressEntity);
        log.info("Created user address");
        return ProtectedUserDto.mapToDto(userEntity);

    }

    public UUID deleteUserAddressById(UUID addressId) throws BadRequestException {
        try {
            UserAddressEntity userAddress = userAddressRepository.findById(addressId).orElseThrow(() -> new EntityNotFoundException("Failed to find address."));
            if (userAddress == null) {
                throw new BadRequestException("Failed to find address.");
            }
            UserDetailEntity userDetailEntity = userDetailRepository.findById(userAddress.getUserDetailEntity().getId()).orElseThrow(() -> new EntityNotFoundException("User detail not found"));
            userDetailEntity.setUserAddresses(List.of()); // force remove relationship inorder to make delete effect
            userAddressRepository.delete(userAddress);
            log.info("Deleted user address with ID: {}", addressId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new BadRequestException(ex.getMessage());
        }
        return addressId;
    }

    public UserAddressEntity partiallyUpdateAddress(PartialUpdateUserAddressReqDto partialUpdateUserAddressReqDto) throws BadRequestException {
        UserAddressEntity userAddress = userAddressRepository.findById(partialUpdateUserAddressReqDto.getId()).orElseThrow(() -> new EntityNotFoundException("Failed to find address."));
        if (userAddress == null) {
            throw new BadRequestException("Failed to find address.");
        }

        if (partialUpdateUserAddressReqDto.getDescriptiveAddress() != null) {
            userAddress.setDescriptiveAddress(partialUpdateUserAddressReqDto.getDescriptiveAddress());
        }

        if (partialUpdateUserAddressReqDto.getCity() != null) {
            userAddress.setCity(partialUpdateUserAddressReqDto.getCity());
        }

        if (partialUpdateUserAddressReqDto.getState() != null) {
            userAddress.setState(partialUpdateUserAddressReqDto.getState());
        }
        if (partialUpdateUserAddressReqDto.getCountry() != null) {
            userAddress.setCountry(partialUpdateUserAddressReqDto.getCountry());
        }
        if (partialUpdateUserAddressReqDto.getStreet() != null) {
            userAddress.setStreet(partialUpdateUserAddressReqDto.getStreet());
        }
        if (partialUpdateUserAddressReqDto.getPostalCode() != null) {
            userAddress.setPostalCode(partialUpdateUserAddressReqDto.getPostalCode());
        }
        if (partialUpdateUserAddressReqDto.getHouse() != null) {
            userAddress.setHouse(partialUpdateUserAddressReqDto.getHouse());
        }
        if (partialUpdateUserAddressReqDto.getPrimaryPhoneNumber() != null) {
            userAddress.setPrimaryPhoneNumber(partialUpdateUserAddressReqDto.getPrimaryPhoneNumber());
        }
        if (partialUpdateUserAddressReqDto.getSecondaryPhoneNumber() != null) {
            userAddress.setSecondaryPhoneNumber(partialUpdateUserAddressReqDto.getSecondaryPhoneNumber());
        }


        userAddress = userAddressRepository.save(userAddress);
        return userAddress;
    }

}

