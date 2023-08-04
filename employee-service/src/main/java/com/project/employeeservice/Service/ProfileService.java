package com.project.employeeservice.Service;

import com.project.employeeservice.Exception.FailToUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@Service
public class ProfileService {
    private final S3Client s3Client;

    @Value("${app.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String region;

    public ProfileService(S3Client s3Client) {
        this.s3Client = s3Client;
    }
    public String uploadProfile(MultipartFile file) throws FailToUploadException {
        try {
            // 生成唯一的文件名
            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            // 创建上传请求
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .build();

            // 从MultipartFile获取输入流，上传到S3
            PutObjectResponse response = s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return getFileUrl(filename);
        } catch (Exception e) {
            throw new FailToUploadException("Failed to upload file");
        }
    }




    private String getFileUrl(String filename) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, filename);
    }
}
