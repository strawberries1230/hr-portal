package com.project.applicationservice.Service;

import com.project.applicationservice.DAO.DocumentRepository;
import com.project.applicationservice.Exception.FailToUploadException;
import com.project.applicationservice.Model.PersonalDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {
    private final S3Client s3Client;
    private final DocumentRepository documentRepository;

    @Value("${app.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String region;

    public DocumentService(S3Client s3Client, DocumentRepository documentRepository) {
        this.s3Client = s3Client;
        this.documentRepository = documentRepository;
    }
    public String uploadDocuments(String email, MultipartFile file, String type, Boolean isRequired) throws FailToUploadException {
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

            String url = getFileUrl(filename);

            Optional<PersonalDocument> personalDocumentOptional = documentRepository.findByEmailAndType(email, type);
            //create a document
            if(personalDocumentOptional.isEmpty()) {
                PersonalDocument personalDocument = new PersonalDocument();
                personalDocument.setEmail(email);
                personalDocument.setType(type);
                personalDocument.setRequired(isRequired);
                personalDocument.setPath(url);
                personalDocument.setComment("");
                personalDocument.setStatus("pending");
                documentRepository.save(personalDocument);
            }
            //document exists
            else {
                PersonalDocument personalDocument = personalDocumentOptional.get();
                personalDocument.setPath(url);
                documentRepository.save(personalDocument);
            }
            return url;
        }
        catch (Exception e) {
            throw new FailToUploadException("Failed to upload file");
        }
    }


    private String getFileUrl(String filename) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, filename);
    }
}
