package com.sopt.bbangzip.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/success")
    public ResponseEntity<TestDto> testSuccess(){
        return ResponseEntity.ok(TestDto.builder().content("얼른 자고싶어..").build());
    }
}
