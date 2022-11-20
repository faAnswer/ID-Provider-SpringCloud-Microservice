package org.tecky.storageservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.faAnswer.web.util.json.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tecky.storageservice.services.intf.IStorageService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/res/storage")
@Slf4j
public class ResourceController {

    @Autowired
    IStorageService iStorageService;

    private Pattern pattern = Pattern.compile("\\.[a-zA-Z]+$");


    @PostMapping(value = "/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile pic, @RequestParam("bucketName") String bucketName) {

        String oriFileName = pic.getOriginalFilename();

        Matcher matcher = this.pattern.matcher(oriFileName);

        String fileExtension = "";

        while (matcher.find()) {

            fileExtension += matcher.group(0);
        }

        ResponseEntity<?> responseEntity;
        
        try {

            responseEntity = iStorageService.save(bucketName, fileExtension, pic);

        } catch (Exception e) {
            log.info("Upload Failed！", e);
            return ResponseEntity.badRequest().build();
        }

        return responseEntity;
    }
}
