package com.service.user.service.address;

import com.service.user.entity.AddressEntity;

import java.util.List;

public interface AddressService {

    AddressEntity getAddressByAddressId(String addressId, List<AddressEntity> addressEntityList);

}
