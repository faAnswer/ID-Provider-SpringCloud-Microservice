package org.tecky.uaaservice.services.intf;

import org.faAnswer.web.util.json.JSONResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tecky.uaaservice.entities.UserEntity;

public interface IRegService {

    public ResponseEntity<JSONResponse<Object>> regNewUser(UserEntity userEntity);


}
