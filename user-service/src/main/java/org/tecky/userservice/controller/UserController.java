package org.tecky.userservice.controller;


import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tecky.userservice.service.intf.IUserPicService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    MinioClient minioClient;

    @Autowired
    IUserPicService iUserPicService;

    @GetMapping ("/pic")
    public String findPicUrl(@RequestParam("uid") Integer uid) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {


        return iUserPicService.getObjectUrl(uid);
    }

    @PostMapping ("/pic")
    public String upload(@RequestParam("pic") MultipartFile pic)  {

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
            return "failed";
        }
        return "done";
    }
}
