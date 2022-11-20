package org.tecky.userservice.controller;


import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tecky.userservice.service.intf.IUserPicService;
import org.tecky.userservice.service.intf.IUserRegService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(value = "*")
public class UserController {

    @Autowired
    MinioClient minioClient;

    @Autowired
    IUserPicService iUserPicService;

    @GetMapping ("/pic")
    public String findPicUrl(@RequestParam("uid") Integer uid) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {


        return iUserPicService.getObjectUrl(uid);
    }

    @PostMapping ("/pic")
    public ResponseEntity<?> upload(@RequestParam("pic") MultipartFile pic)  {

        String oriFileName = pic.getOriginalFilename();

        Pattern pattern = Pattern.compile("\\.[a-zA-Z]+$");
        Matcher matcher = pattern.matcher(oriFileName);

        String fileExtension = "";

        while (matcher.find()) {

            fileExtension += matcher.group(0);
        }

        try {

            iUserPicService.save(fileExtension, pic);

        } catch (Exception e) {
            log.info("Upload Failed！", e);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @Autowired
    IUserRegService iUserRegService;

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<?> register(@RequestBody Map<String, String> regInfo, HttpSession httpSession) throws NoSuchAlgorithmException {

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(regInfo.get("email"));
        userEntity.setUsername(regInfo.get("username"));

        UserEntity regUser = iUserRegService.reg(userEntity, regInfo.get("password"));

        httpSession.setAttribute("user", regUser.getUsername());

        return ResponseEntity.ok().build();
    }
}
