package com.iabur.ws.springbootfirst.service.impl;

import com.iabur.ws.springbootfirst.io.entity.UserEntity;
import com.iabur.ws.springbootfirst.io.repository.UserRepository;
import com.iabur.ws.springbootfirst.service.UserService;
import com.iabur.ws.springbootfirst.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        userEntity.setEncryptedPassword("testPassword");
        userEntity.setUserId("testId");

        UserEntity storedValue = userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedValue, returnValue);

        return returnValue;
    }
}
