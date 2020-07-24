package com.iabur.ws.springbootfirst.service;

import com.iabur.ws.springbootfirst.shared.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
