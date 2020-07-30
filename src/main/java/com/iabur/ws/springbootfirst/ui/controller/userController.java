package com.iabur.ws.springbootfirst.ui.controller;

import com.iabur.ws.springbootfirst.service.UserService;
import com.iabur.ws.springbootfirst.shared.dto.UserDto;
import com.iabur.ws.springbootfirst.ui.model.request.UserDetailsRequestModel;
import com.iabur.ws.springbootfirst.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class userController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id) {
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        UserDto userDto = new UserDto();
        UserRest returnValue = new UserRest();
        BeanUtils.copyProperties(userDetails,userDto);
        UserDto createUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createUser, returnValue);
        return returnValue;
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
