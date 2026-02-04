package com.unir.ms_books_catalogue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
