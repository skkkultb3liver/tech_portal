package com.dev.blogs4u.authentication.controllers;

import com.dev.blogs4u.authentication.services.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GreetingsController {

    @GetMapping("/blogs")
    public String blogsHandler() {
        return "All users had access to this page";
    }

    @GetMapping("/secured")
    public String securedPageHandler() {
        return "Only authenticated users had access to this page";
    }


}
