package com.evergreen.evergreenmedic.controllers;

import com.evergreen.evergreenmedic.dtos.ProtectedUserDto;
import com.evergreen.evergreenmedic.dtos.requests.CreateNewUserRequestDto;
import com.evergreen.evergreenmedic.dtos.requests.user.UpdateCompleteUserReqDto;
import com.evergreen.evergreenmedic.dtos.requests.user.UpdateUserEmailReqDto;
import com.evergreen.evergreenmedic.dtos.requests.user.UpdateUserPasswordReqDto;
import com.evergreen.evergreenmedic.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<ProtectedUserDto>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    @PostMapping()
    public ResponseEntity<ProtectedUserDto> createNewUser(@RequestBody CreateNewUserRequestDto createNewUserRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(createNewUserRequestDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<ProtectedUserDto>> getUserById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Integer> deleteUserById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserById(id));
    }

    @PutMapping()
    public ResponseEntity<ProtectedUserDto> updateUser(@RequestBody UpdateCompleteUserReqDto updateCompleteUserReqDto) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(updateCompleteUserReqDto));
    }

    @PostMapping("/email")
    public ResponseEntity<ProtectedUserDto> updateUserEmail(@RequestBody UpdateUserEmailReqDto updateUserEmailReqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserEmail(updateUserEmailReqDto));
    }

    @PostMapping("/password")
    public ResponseEntity<ProtectedUserDto> updateUserPassword(@RequestBody @Valid UpdateUserPasswordReqDto updateUserPasswordReqDto) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserPassword(updateUserPasswordReqDto));
    }

    @PostMapping("/seed")
    public ResponseEntity<List<ProtectedUserDto>> seedUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.seedUsers());
//        List<ProtectedUserDto> protectedUserDtos = userService.seedUsers();
//        return new ApiResponse<List<ProtectedUserDto>>().OK(200, "success", protectedUserDtos);
    }

}
