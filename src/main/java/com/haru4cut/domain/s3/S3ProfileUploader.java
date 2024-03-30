package com.haru4cut.domain.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class S3ProfileUploader {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
//    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Autowired
    public S3ProfileUploader(@Qualifier("profileAmazonS3") AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String saveFile(MultipartFile multipartFile) throws IOException {
        String name = multipartFile.getName();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType("image/png");

        amazonS3.putObject(bucketName + "/profile", multipartFile.getName(), multipartFile.getInputStream(), metadata);

        return amazonS3.getUrl(bucketName + "/profile", name).toString();
    }

}
