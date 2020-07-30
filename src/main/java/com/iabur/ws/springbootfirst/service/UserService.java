package com.iabur.ws.springbootfirst.service;

import com.iabur.ws.springbootfirst.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUser(String username);
}
