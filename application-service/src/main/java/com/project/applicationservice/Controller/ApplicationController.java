package com.project.applicationservice.Controller;

import com.project.applicationservice.Exception.*;
import com.project.applicationservice.Model.PersonalApplication;
import com.project.applicationservice.Model.PersonalDocument;
import com.project.applicationservice.Service.ApplicationService;
import com.project.applicationservice.Service.DocumentService;
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

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/application")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final DocumentService documentService;

    @Value("${app.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String region;

    private final S3Client s3Client;

    public ApplicationController(ApplicationService applicationService, DocumentService documentService, S3Client s3Client) {
        this.applicationService = applicationService;
        this.documentService = documentService;
        this.s3Client = s3Client;
    }

    //    @GetMapping()
//    ResponseEntity<?> get() {
//        return ResponseEntity.ok("you get it!");
//    }
    @GetMapping("/oauth/callback")
    public String oauthCallback(@RequestParam("code") String code) {
        // 在这里处理获取的授权代码
        // 你可以将 code 用于获取访问令牌和刷新令牌
        return "Authorization code received: " + code;
    }


    @PostMapping("/create")
    ResponseEntity<?> startApplication(@RequestHeader("X-User-Roles") String roles, @RequestHeader("X-User-Email") String email) throws AccessDeniedException, AlreadyExistsException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_EMPLOYEE")) {
            throw new AccessDeniedException("Access denied, you need employee access");
        }
        if (applicationService.findByEmail(email).isPresent()) {
            throw new AlreadyExistsException(String.format("application already exists with email: %s", email));
        }
        applicationService.startApplication(email);
        return ResponseEntity.ok("You've started an application!");
    }

//    @GetMapping("/{email}")
//    ResponseEntity<?> getApplication(HttpServletRequest request, @PathVariable String email)  {
//
//        Optional<PersonalApplication> personalApplication = applicationService.findByEmail(email);
//        return ResponseEntity.ok(personalApplication.get());
//    }

    @PutMapping("/{email}")
    ResponseEntity<?> editApplication(@RequestHeader("X-User-Roles") String roles, @PathVariable String email, @RequestParam String status, @RequestParam String comment) throws AccessDeniedException, NotFoundException, DataFormatException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        PersonalApplication personalApplication = applicationService.editApplication(email, status, comment);
        return ResponseEntity.ok(personalApplication);
    }

    @PutMapping("/edit/{email}/{type}")
    ResponseEntity<?> editDocument(@RequestHeader("X-User-Roles") String roles, @PathVariable String email, @PathVariable String type, @RequestParam String status, @RequestParam String comment) throws AccessDeniedException, NotFoundException {

        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        PersonalDocument personalDocument = documentService.editDocument(email, type, status, comment);
        return ResponseEntity.ok(personalDocument);
    }

    @PostMapping("/upload-documents")
    public ResponseEntity<?> uploadDocuments(@RequestHeader("X-User-Roles") String roles, @RequestHeader("X-User-Email") String email, @RequestParam("file") MultipartFile file, @RequestParam Boolean required, @RequestParam String type) throws AccessDeniedException, FailToUploadException, NotFoundException {

        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_EMPLOYEE")) {
            throw new AccessDeniedException("Access denied, you need employee access");
        }
        String url = documentService.uploadDocuments(email, file, type, required);
        return ResponseEntity.ok(String.format("You uploaded to: %s", url));
    }

    @GetMapping("/download/{documentName}")
    public ResponseEntity<InputStreamResource> getDocument(@RequestHeader("X-User-Roles") String roles, @PathVariable String documentName) throws NotFoundException, AccessDeniedException {

        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_EMPLOYEE")) {
            throw new AccessDeniedException("Access denied, you need employee access");
        }
        try {
            // 创建下载请求
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(documentName)
                    .build();

            // 从S3下载图像
            ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObjectAsBytes(getObjectRequest);
            byte[] imageData = responseBytes.asByteArray();

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(getContentType(documentName))); // 根据图像类型设置正确的Content-Type
            // 可根据需要设置其他响应头，例如缓存控制等

            return new ResponseEntity<>(new InputStreamResource(new ByteArrayInputStream(imageData)), headers, HttpStatus.OK);
        } catch (NoSuchKeyException e) {
            throw new NotFoundException(String.format("Image not found with: %s", documentName));
        }
    }

    private String getContentType(String fileName) {
        if (fileName.endsWith(".pdf")) {
            return "application/pdf";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        }
        // 可以添加更多文件类型
        return "application/octet-stream"; // 默认类型
    }


}
