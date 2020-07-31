package com.iabur.ws.springbootfirst.ui.controller;

import com.iabur.ws.springbootfirst.service.UserService;
import com.iabur.ws.springbootfirst.shared.dto.UserDto;
import com.iabur.ws.springbootfirst.ui.model.request.UserDetailsRequestModel;
import com.iabur.ws.springbootfirst.ui.model.response.OperationStatusModel;
import com.iabur.ws.springbootfirst.ui.model.response.RequestOperationStatus;
import com.iabur.ws.springbootfirst.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class userController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id) {
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;
    }

    @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        UserDto userDto = new UserDto();
        UserRest returnValue = new UserRest();
        BeanUtils.copyProperties(userDetails, userDto);
        UserDto createUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createUser, returnValue);
        return returnValue;
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        UserDto updateUser = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(updateUser, returnValue);

        return returnValue;
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(id);
        returnValue.setOperationStatus(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "5") int limit) {
        List<UserRest> returnValues = new ArrayList<>();
        List<UserDto> users = userService.getUsers(page, limit);
        for (UserDto user :
                users) {
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(user, userModel);
            returnValues.add(userModel);
        }
        return returnValues;
    }
}