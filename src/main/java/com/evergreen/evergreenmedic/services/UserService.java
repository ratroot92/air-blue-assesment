package com.evergreen.evergreenmedic.services;


import com.evergreen.evergreenmedic.dtos.ProtectedUserDto;
import com.evergreen.evergreenmedic.dtos.UserDto;
import com.evergreen.evergreenmedic.dtos.requests.CreateNewUserRequestDto;
import com.evergreen.evergreenmedic.dtos.requests.user.UpdateCompleteUserReqDto;
import com.evergreen.evergreenmedic.dtos.requests.user.UpdateUserEmailReqDto;
import com.evergreen.evergreenmedic.dtos.requests.user.UpdateUserPasswordReqDto;
import com.evergreen.evergreenmedic.entities.UserDetailEntity;
import com.evergreen.evergreenmedic.entities.UserEntity;
import com.evergreen.evergreenmedic.repositories.UserDetailRepository;
import com.evergreen.evergreenmedic.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailRepository userDetailRepository;

    public Optional<ProtectedUserDto> getUserById(Integer id) {
//        Optional<UserEntity> userEntity = Optional.ofNullable(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
        Optional<UserEntity> userEntity = Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found.")));
        ProtectedUserDto userDto = UserDto.mapToDto(userEntity.get());
        return Optional.of(userDto);
    }

    public List<ProtectedUserDto> getUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<ProtectedUserDto> usersDtos = new ArrayList<>();
        for (UserEntity user : userEntities) {
            usersDtos.add(UserDto.mapToDto(user));
        }

        return usersDtos;
    }

    public ProtectedUserDto createNewUser(CreateNewUserRequestDto createNewUserRequestDto) {
        String firstName = createNewUserRequestDto.getFirstName();
        String lastName = createNewUserRequestDto.getLastName();
        String password = createNewUserRequestDto.getPassword();
        String email = createNewUserRequestDto.getEmail();
        String phoneNumber = createNewUserRequestDto.getPhoneNumber();
        UserDto userDto = new UserDto();
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setPassword(passwordEncoder.encode("66068957"));
        userDto.setEmail(email);
        userDto.setPhoneNumber(phoneNumber);
        UserEntity userEntity = UserDto.mapToEntity(userDto);
        UserDetailEntity userDetailEntity = new UserDetailEntity();
        userDetailEntity.setUser(userEntity);
        userDetailEntity = userDetailRepository.save(userDetailEntity);
//        userEntity.setUserDetailEntity(new userDetailEntity());
        userEntity = userRepository.save(userEntity);
        ProtectedUserDto protectedUserDto = ProtectedUserDto.mapToDto(userEntity);
        return protectedUserDto;
    }

    public Integer deleteUserById(Integer id) {
        Optional<UserEntity> userEntity = Optional.ofNullable(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
        userRepository.deleteById(id);
        return id;
    }

    public ProtectedUserDto updateUser(UpdateCompleteUserReqDto updateCompleteUserReqDto) {

        String firstName = updateCompleteUserReqDto.getFirstName();
        String lastName = updateCompleteUserReqDto.getLastName();
        Integer userId = updateCompleteUserReqDto.getId();

        UserEntity userEntity = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);


        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userRepository.save(userEntity);

        ProtectedUserDto protectedUserDto = ProtectedUserDto.mapToDto(userEntity);

        return protectedUserDto;
    }

    public List<ProtectedUserDto> seedUsers() {
        userRepository.deleteAll();
        UserEntity userEntity1 = new UserEntity();
        List<UserEntity> userEntities = new ArrayList<>();
        userEntity1.setFirstName("Ahmad");
        userEntity1.setLastName("Kabeer");
        userEntity1.setEmail("maliksblr92@gmail.com");
        userEntity1.setPhoneNumber("00923441500542");
        userEntity1.setPassword(passwordEncoder.encode("66068957"));
        userEntities.add(userEntity1);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setFirstName("Usman");
        userEntity2.setLastName("Raza");
        userEntity2.setEmail("usman@gmail.com");
        userEntity2.setPhoneNumber("00923441500542");
        userEntity2.setPassword(passwordEncoder.encode("66068957"));
        userEntities.add(userEntity2);

        UserEntity userEntity3 = new UserEntity();
        userEntity3.setFirstName("Salman");
        userEntity3.setLastName("Usmani");
        userEntity3.setEmail("salman@gmail.com");
        userEntity3.setPhoneNumber("00923441500542");
        userEntity3.setPassword(passwordEncoder.encode("66068957"));
        userEntities.add(userEntity3);
        userEntities = userRepository.saveAll(userEntities);
        List<ProtectedUserDto> usersDtos = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            usersDtos.add(ProtectedUserDto.mapToDto(userEntity));
        }
        return usersDtos;
    }

    public ProtectedUserDto updateUserEmail(UpdateUserEmailReqDto updateUserEmailReqDto) {
        String providedEmail = updateUserEmailReqDto.getEmail();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getPrincipal().toString();
        if (userEmail.equals(providedEmail)) {
            throw new DuplicateKeyException("User email already exists");
        } else {
            UserEntity user = userRepository.findByEmail(userEmail).orElseThrow(EntityNotFoundException::new);
            user.setEmail(providedEmail);
            user = userRepository.save(user);
            ProtectedUserDto protectedUserDto = ProtectedUserDto.mapToDto(user);
            return protectedUserDto;
        }
//        Update user authentication otherwise consecutive request will fail


    }

    public ProtectedUserDto updateUserPassword(UpdateUserPasswordReqDto updateUserPasswordReqDto) throws BadRequestException {
        try {
            String password = updateUserPasswordReqDto.getPassword();
            String confirmPassword = updateUserPasswordReqDto.getConfirmPassword();
            if (!password.equals(confirmPassword)) {
                throw new BadRequestException("Passwords do not match");
            } else {
                String hashedPassword = passwordEncoder.encode(password);
                UserEntity userEntity = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()).orElseThrow(EntityNotFoundException::new);
                userEntity.setPassword(hashedPassword);
                userRepository.save(userEntity);
                ProtectedUserDto protectedUserDto = ProtectedUserDto.mapToDto(userEntity);
                return protectedUserDto;
            }
        } catch (BadRequestException exception) {
            throw new BadRequestException(exception.getMessage());
        }
    }

}
