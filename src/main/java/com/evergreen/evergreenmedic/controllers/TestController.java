package com.evergreen.evergreenmedic.controllers;

import com.evergreen.evergreenmedic.dtos.requests.test.TestPostReqDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    public TestController() {
    }

    @PostMapping
    public String test(@RequestBody @Valid TestPostReqDto testPostReqDto) {
        System.out.println(testPostReqDto.toString());
        return "test";
    }
}
