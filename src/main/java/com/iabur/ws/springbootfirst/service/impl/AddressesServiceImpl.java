package com.iabur.ws.springbootfirst.service.impl;

import com.iabur.ws.springbootfirst.io.entity.AddressEntity;
import com.iabur.ws.springbootfirst.io.entity.UserEntity;
import com.iabur.ws.springbootfirst.io.repository.AddressRepository;
import com.iabur.ws.springbootfirst.io.repository.UserRepository;
import com.iabur.ws.springbootfirst.service.AddressesService;
import com.iabur.ws.springbootfirst.shared.dto.AddressDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressesServiceImpl implements AddressesService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDTO> getAddresses(String userId) {
        List<AddressDTO> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) return returnValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for (AddressEntity addressEntity : addresses) {
            returnValue.add(modelMapper.map(addressEntity, AddressDTO.class));
        }
        return returnValue;
    }

    @Override
    public AddressDTO getAddress(String addressId) {
        AddressDTO returnValue = null;
        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
        if (addressEntity != null) {
            returnValue = new ModelMapper().map(addressEntity, AddressDTO.class);
        }
        return returnValue;
    }
}
