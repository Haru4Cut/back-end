package com.haru4cut.domain.basic;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity healthyCheck() {
        return ResponseEntity.ok("ok");
    }
}
