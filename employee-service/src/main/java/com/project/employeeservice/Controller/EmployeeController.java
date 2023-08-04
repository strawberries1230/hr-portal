package com.project.employeeservice.Controller;

import com.project.employeeservice.Entity.DTO.EmployeeDTO;
import com.project.employeeservice.Exception.FailToUploadException;
import com.project.employeeservice.Service.EmployeeService;
import com.project.employeeservice.Service.ProfileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ProfileService profileService;

//    private final S3Client s3Client;
//    @Value("${app.s3.bucket}")
//    private String bucketName;
//    @Value("${cloud.aws.region.static}")
//    private String region;


    public EmployeeController(EmployeeService employeeService, S3Client s3Client, ProfileService profileService, S3Client s3Client1) {
        this.employeeService = employeeService;
        this.profileService = profileService;
//        this.s3Client = s3Client1;
    }

    @GetMapping()
    public ResponseEntity<?> get() {
        return new ResponseEntity<>("GOOOOOOOOOD!", HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.createEmployee(employeeDTO);
        return ResponseEntity.ok("Employee info saved!!");
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadProfile(@RequestParam("file") MultipartFile file) {
        try {
            String url = profileService.uploadProfile(file);
            return ResponseEntity.ok(url);
        } catch (FailToUploadException e) {
            return ResponseEntity.badRequest().body("fail to upload!!!");
        }
    }
//    @GetMapping("/{imageName}")
//    public ResponseEntity<InputStreamResource> getProfileImage(@PathVariable String imageName) throws FailToUploadException {
//        try {
//            // 创建下载请求
//            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(imageName)
//                    .build();
//
//            // 从S3下载图像
//            ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObjectAsBytes(getObjectRequest);
//            byte[] imageData = responseBytes.asByteArray();
//
//            // 设置响应头
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG); // 根据图像类型设置正确的Content-Type
//            // 可根据需要设置其他响应头，例如缓存控制等
//
//            return new ResponseEntity<>(new InputStreamResource(new ByteArrayInputStream(imageData)), headers, HttpStatus.OK);
//        } catch (NoSuchKeyException e) {
//            throw new FailToUploadException("Image not found");
//        } catch (Exception e) {
//            throw new FailToUploadException("Exception");
//        }
//    }


}
