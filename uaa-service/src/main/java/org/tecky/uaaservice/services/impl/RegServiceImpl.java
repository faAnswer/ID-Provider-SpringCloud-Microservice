package org.tecky.uaaservice.services.impl;

import org.faAnswer.web.util.json.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tecky.uaaservice.entities.UserEntity;
import org.tecky.uaaservice.entities.UserRoleEntity;
import org.tecky.uaaservice.mapper.UserEntityRepository;
import org.tecky.uaaservice.mapper.UserRoleEntityRepository;
import org.tecky.uaaservice.services.intf.IRegService;

@Service
public class RegServiceImpl implements IRegService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    UserRoleEntityRepository userRoleEntityRepository;

    @Override
    public ResponseEntity<JSONResponse<Object>> regNewUser(UserEntity userEntity) {

        UserEntity checkUserEntity;

        checkUserEntity = userEntityRepository.findByUsername(userEntity.getUsername());

        if(checkUserEntity != null) {

            return JSONResponse.fail("Username is already in use", HttpStatus.CONFLICT);
        }

        checkUserEntity = userEntityRepository.findByEmail(userEntity.getEmail());

        if(checkUserEntity != null) {

            return JSONResponse.fail("Email is already in use", HttpStatus.CONFLICT);
        }

        userEntity.setShapassword(passwordEncoder.encode(userEntity.getShapassword()));

        userEntityRepository.save(userEntity);

        Integer uid = userEntityRepository.findByUsername(userEntity.getUsername()).getUid();
        UserRoleEntity roleEntity = new UserRoleEntity();
        roleEntity.setUid(uid);
        roleEntity.setRoleid(2);
        userRoleEntityRepository.save(roleEntity);

        return JSONResponse.ok("username", userEntity.getUsername());
    }
}
