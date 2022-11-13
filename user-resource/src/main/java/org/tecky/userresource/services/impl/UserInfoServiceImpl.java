package org.tecky.userresource.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tecky.userresource.entities.UserEntity;
import org.tecky.userresource.mapper.UserEntityRepository;
import org.tecky.userresource.services.intf.IUserInfoService;

@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    UserEntityRepository userEntityRepository;

    @Override
    public String getInfo(String username) throws JsonProcessingException {

        UserEntity userEntity = userEntityRepository.findByUsername(username);
        userEntity.setUsername("CORE_" + username);

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonString = objectMapper.writeValueAsString(userEntity);

        return jsonString;
    }
}
