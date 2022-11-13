package org.tecky.userresource.services.intf;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IUserInfoService {

    public String getInfo(String username) throws JsonProcessingException;
}
