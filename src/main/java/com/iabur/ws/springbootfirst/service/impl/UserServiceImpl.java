package com.iabur.ws.springbootfirst.service.impl;

import com.iabur.ws.springbootfirst.io.entity.UserEntity;
import com.iabur.ws.springbootfirst.io.repository.UserRepository;
import com.iabur.ws.springbootfirst.service.UserService;
import com.iabur.ws.springbootfirst.shared.Utils;
import com.iabur.ws.springbootfirst.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Override
    public UserDto createUser(UserDto userDto) {

        if(userRepository.findByEmail(userDto.getEmail())!=null) throw new RuntimeException("User already exist");
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        String publicUserId = utils.generateUserId(30);

        userEntity.setEncryptedPassword(publicUserId);
        userEntity.setUserId("testId");

        UserEntity storedValue = userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedValue, returnValue);

        return returnValue;
    }
}
