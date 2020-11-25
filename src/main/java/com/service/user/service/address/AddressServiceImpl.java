package com.service.user.service.address;

import com.service.user.entity.AddressEntity;
import com.service.user.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public AddressEntity getAddressByAddressId(String addressId, List<AddressEntity> addressEntityList) {
        AddressEntity addressEntityByAddressId = null;

        for (AddressEntity addressEntity : addressEntityList) {
            if (addressEntity.getAddressId().equals(addressId)) {
                addressEntityByAddressId = addressRepository.findByAddressId(addressId);
            }
        }
        return addressEntityByAddressId;
    }
}
