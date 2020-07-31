package com.iabur.ws.springbootfirst.service.impl;

import com.iabur.ws.springbootfirst.exceptions.UserServiceException;
import com.iabur.ws.springbootfirst.io.entity.UserEntity;
import com.iabur.ws.springbootfirst.io.repository.UserRepository;
import com.iabur.ws.springbootfirst.service.UserService;
import com.iabur.ws.springbootfirst.shared.Utils;
import com.iabur.ws.springbootfirst.shared.dto.UserDto;
import com.iabur.ws.springbootfirst.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        if (userRepository.findByEmail(userDto.getEmail()) != null)
            throw new UserServiceException(ErrorMessages.EMAIL_ADDRESS_NOT_VERIFIED.getErrorMessage());
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        String publicUserId = utils.generateUserId(30);

        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        UserEntity storedValue = userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedValue, returnValue);


        return returnValue;
    }

    @Override
    public UserDto getUser(String username) {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null) throw new UsernameNotFoundException(username);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UsernameNotFoundException("User with ID: " + userId + " not found");

        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) {
        UserDto returnValue = new UserDto();

        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserEntity updatedUserDetails = userRepository.save(userEntity);
        BeanUtils.copyProperties(updatedUserDetails, returnValue);
        return returnValue;
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();
        Pageable pageRequest = PageRequest.of(page, limit);
        Page<UserEntity> userEntityPage = userRepository.findAll(pageRequest);
        List<UserEntity> users = userEntityPage.getContent();
        for (UserEntity user :
                users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            returnValue.add(userDto);
        }
        return returnValue;
    }
}
