package com.JobPortal.ServiceImpl;

import com.JobPortal.Model.Address;
import com.JobPortal.Repository.AddressRepository;
import com.JobPortal.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Override
    public Address addUserAddress(Address address) {
        return addressRepository.save(address);
    }
}
