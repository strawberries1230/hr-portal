package com.project.employeeservice.Service;

import com.project.employeeservice.DAO.EmployeeRepository;
import com.project.employeeservice.Entity.Document.Employee;
import com.project.employeeservice.Exception.FailToUploadException;
import com.project.employeeservice.Exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.UUID;

@Service
public class ProfileService {
    private final S3Client s3Client;

    @Value("${app.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String region;

    private final EmployeeRepository employeeRepository;
    public ProfileService(S3Client s3Client, EmployeeRepository employeeRepository) {
        this.s3Client = s3Client;
        this.employeeRepository = employeeRepository;
    }
    public String uploadProfile(String email, MultipartFile file) throws FailToUploadException, UserNotFoundException {
        try {

            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .build();


            PutObjectResponse response = s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            Employee employee = employeeRepository.findByEmail(email);
            if(employee == null) {
                throw new UserNotFoundException("User not found with email: "+email);
            }
            String url = getFileUrl(filename);
            employee.setProfilePicture(url);

            return url;
        }catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found with email: "+email);
        }
        catch (Exception e) {
            throw new FailToUploadException("Failed to upload file");
        }
    }

    private String getFileUrl(String filename) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, filename);
    }
}
