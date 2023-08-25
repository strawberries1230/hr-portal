package com.project.employeeservice.Controller;

import com.project.employeeservice.Entity.DTO.EmployeeDTO;
import com.project.employeeservice.Exception.*;
import com.project.employeeservice.Feign.HousingClient;
import com.project.employeeservice.Service.EmployeeService;
import com.project.employeeservice.Service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
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


    public EmployeeController(EmployeeService employeeService, ProfileService profileService, HousingClient housingClient) {
        this.employeeService = employeeService;
        this.profileService = profileService;
//        this.s3Client = s3Client;

    }


    @GetMapping()
    public ResponseEntity<?> get() {

        return new ResponseEntity<>("GOOOOOOOOOD!", HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestHeader("X-User-Roles") String roles, @RequestHeader("X-User-Email") String email, @RequestBody EmployeeDTO employeeDTO) throws AccessDeniedException, UserAlreadyExistsException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_EMPLOYEE")) {
            throw new AccessDeniedException("Access denied");
        }
        employeeService.createEmployee(email, employeeDTO);
        return ResponseEntity.ok("Employee info saved!!");
    }


    @PostMapping("/upload")
    public ResponseEntity<?> uploadProfile(@RequestHeader("X-User-Roles") String roles, @RequestHeader("X-User-Email") String email,@RequestParam("file") MultipartFile file) throws UserNotFoundException, FailToUploadException, AccessDeniedException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_EMPLOYEE")) {
            throw new AccessDeniedException("Access denied");
        }
        String url = profileService.uploadProfile(email, file);
        return ResponseEntity.ok(url);

    }

    @PutMapping("/edit")
    public void editEmployee(@RequestHeader("X-User-Roles") String roles, @RequestHeader("X-User-Email") String email,@RequestBody EmployeeDTO employeeDTO) throws UserNotFoundException, AccessDeniedException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_EMPLOYEE")) {
            throw new AccessDeniedException("Access denied");
        }
        employeeService.editEmployee(email, employeeDTO);
    }

    @PutMapping("/assign-house")
    public ResponseEntity<?> assignHouse(@RequestHeader("X-User-Roles") String roles,@RequestParam("hid") Long houseId, @RequestParam("email") String email) throws UserNotFoundException, AccessDeniedException, FailToAssignHouseException {
        List<String> roleList = Arrays.asList(roles.split(","));
        System.out.println(roleList);
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        employeeService.assignHouse(roles, houseId, email);
        return ResponseEntity.ok(String.format("House with id %s is assigned to employee with email %s", houseId, email));

    }
    //Test if the image can be retrieved
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
