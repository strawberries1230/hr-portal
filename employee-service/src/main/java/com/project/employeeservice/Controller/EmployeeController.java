package com.project.employeeservice.Controller;

import com.project.employeeservice.Entity.DTO.EmployeeDTO;
import com.project.employeeservice.Exception.AccessDeniedException;
import com.project.employeeservice.Exception.FailToUploadException;
import com.project.employeeservice.Exception.UserNotFoundException;
import com.project.employeeservice.Service.EmployeeService;
import com.project.employeeservice.Service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


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
    public ResponseEntity<?> createEmployee(HttpServletRequest request, @RequestBody EmployeeDTO employeeDTO) throws AccessDeniedException {
        List<String> roles = (List<String>) request.getAttribute("roles");
        if (!roles.contains("ROLE_EMPLOYEE")) {
            throw new AccessDeniedException("Access denied");
        }
        String email = (String) request.getAttribute("email");
        employeeService.createEmployee(email, employeeDTO);
        return ResponseEntity.ok("Employee info saved!!");
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadProfile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws UserNotFoundException, FailToUploadException, AccessDeniedException {
        List<String> roles = (List<String>) request.getAttribute("roles");
        if (!roles.contains("ROLE_EMPLOYEE")) {
            throw new AccessDeniedException("Access denied");
        }
        String email = (String) request.getAttribute("email");
        String url = profileService.uploadProfile(email, file);
        return ResponseEntity.ok(url);

    }

    @PutMapping("/edit")
    public void editEmployee(HttpServletRequest request, @RequestBody EmployeeDTO employeeDTO) throws UserNotFoundException, AccessDeniedException {
        List<String> roles = (List<String>) request.getAttribute("roles");
        if (!roles.contains("ROLE_EMPLOYEE")) {
            throw new AccessDeniedException("Access denied");
        }
        String email = (String) request.getAttribute("email");
        employeeService.editEmployee(email, employeeDTO);
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
