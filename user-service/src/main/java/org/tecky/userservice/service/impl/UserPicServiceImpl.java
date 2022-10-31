package org.tecky.userservice.service.impl;


import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tecky.common.entities.UserInfoEntity;
import org.tecky.common.mapper.UserInfoEntityRepository;
import org.tecky.userservice.config.MinioProperties;
import org.tecky.userservice.service.intf.IUserPicService;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Service
public class UserPicServiceImpl implements IUserPicService {

    @Autowired
    MinioClient minioClient;

    @Autowired
    UserInfoEntityRepository userInfoEntityRepository;

    @Resource
    private MinioProperties minioProperties;

    @Override
    public String getObjectUrl(Integer uid) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        UserInfoEntity userInfoEntity = userInfoEntityRepository.findByUid(uid);

        String url = "";

        url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(minioProperties.getPicbucketName())
                        .object(userInfoEntity.getPic())
                        .expiry(2, TimeUnit.MINUTES)
                        .build());

        System.out.println(url);

        return url;
    }

    @Override
    public String save(String fileEx, MultipartFile pic) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        InputStream in = pic.getInputStream();

        String fileHash = String.valueOf(pic.hashCode()) + fileEx;

        PutObjectArgs build = PutObjectArgs
                .builder()
                .bucket(minioProperties.getPicbucketName())
                .object(fileHash)
                .stream(in, in.available(), -1)
                .build();

        minioClient.putObject(build);

        return fileHash;
    }
}
