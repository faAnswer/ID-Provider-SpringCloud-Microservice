package org.tecky.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tecky.common.entities.UserEntity;
import org.tecky.common.mapper.UserEntityRepository;
import org.tecky.common.pojo.SHA256;
import org.tecky.userservice.service.intf.IUserRegService;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class UserRegImpl implements IUserRegService {

    @Autowired
    UserEntityRepository userEntityRepository;

    @Override
    public UserEntity reg(UserEntity userEntity, String password) throws NoSuchAlgorithmException {

        String salt = UUID.randomUUID().toString();
        String shapassword = SHA256.digest2String(password + salt);

        userEntity.setShapassword(shapassword);
        userEntity.setSalt(salt);

        return userEntityRepository.save(userEntity);
    }
}
