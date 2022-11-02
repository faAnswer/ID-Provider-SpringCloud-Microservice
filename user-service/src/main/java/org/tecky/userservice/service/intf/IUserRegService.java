package org.tecky.userservice.service.intf;

import org.tecky.common.entities.UserEntity;

import java.security.NoSuchAlgorithmException;

public interface IUserRegService {

    public UserEntity reg(UserEntity userEntity, String password) throws NoSuchAlgorithmException;
}
