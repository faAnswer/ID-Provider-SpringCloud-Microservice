package org.tecky.storageservice.services.impl;

import com.alibaba.fastjson2.JSONObject;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tecky.storageservice.config.MinioProperties;
import org.tecky.storageservice.services.intf.IStorageService;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Service
public class StorageServiceImpl implements IStorageService {

    @Resource
    private MinioProperties minioProperties;

    @Autowired
    MinioClient minioClient;

    @Override
    public String getObjectUrl(String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {


        String url = "";

        url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(minioProperties.getPicbucketName())
                        .object(fileName)
                        .expiry(6, TimeUnit.DAYS)
                        .build());

        System.out.println(url);

        return url;
    }

    @Override
    public ResponseEntity<?> save(String bucketName, String fileEx, MultipartFile pic) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        InputStream in = pic.getInputStream();

        String fileHash = String.valueOf(pic.hashCode()) + fileEx;

        PutObjectArgs build = PutObjectArgs
                .builder()
                .bucket(bucketName)
                .object(fileHash)
                .stream(in, in.available(), -1)
                .build();

        minioClient.putObject(build);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("filename",fileHash);

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }
}
