package com.service.user.service;

import com.service.user.entity.AddressEntity;
import com.service.user.entity.UserEntity;
import com.service.user.model.request.UserDetailsRequest;

import java.util.List;

public interface AddressService {

    AddressEntity getAddressByAddressId(String addressId, List<AddressEntity> addressEntityList);

}
