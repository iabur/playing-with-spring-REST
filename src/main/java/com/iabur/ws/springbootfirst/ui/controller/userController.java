package com.iabur.ws.springbootfirst.ui.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class userController {
    @GetMapping
    public String getUser() {
        return "Get all user";
    }

    @PostMapping
    public String postUser() {
        return "post user";
    }

    @PutMapping
    public String updateUser() {
        return "update user";
    }

    @DeleteMapping
    public String deleteUser() {
        return "Delete user";
    }
}
