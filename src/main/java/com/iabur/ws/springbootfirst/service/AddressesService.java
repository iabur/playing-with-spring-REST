package com.iabur.ws.springbootfirst.service;

import com.iabur.ws.springbootfirst.shared.dto.AddressDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AddressesService {
    List<AddressDTO> getAddresses(String userId);
    AddressDTO getAddress(String addressId);
}
