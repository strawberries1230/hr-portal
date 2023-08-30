package com.project.applicationservice.Service;

import com.project.applicationservice.DAO.ApplicationRepository;
import com.project.applicationservice.DAO.DocumentRepository;
import com.project.applicationservice.Exception.FailToUploadException;
import com.project.applicationservice.Exception.NotFoundException;
import com.project.applicationservice.Model.PersonalApplication;
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
    private final ApplicationRepository applicationRepository;

    @Value("${app.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String region;

    public DocumentService(S3Client s3Client, DocumentRepository documentRepository, ApplicationRepository applicationRepository) {
        this.s3Client = s3Client;
        this.documentRepository = documentRepository;
        this.applicationRepository = applicationRepository;
    }

    public PersonalDocument editDocument(String email, String type, String status, String comment) throws NotFoundException {
        Optional<PersonalDocument> personalDocumentOptional = documentRepository.findByEmailAndType(email, type);
        if (personalDocumentOptional.isEmpty()) {
            throw new NotFoundException(String.format("Document with email %s and type: %s is not found!", email, type));
        }
        PersonalDocument personalDocument = personalDocumentOptional.get();
        personalDocument.setStatus(status);
        personalDocument.setComment(comment);

        return documentRepository.save(personalDocument);
    }

    public String uploadDocuments(String email, MultipartFile file, String type, Boolean isRequired) throws FailToUploadException, NotFoundException {
        try {
            // generate unique file name
            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            //  upload request
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .build();

            // get input stream from MultipartFile, upload to S3
            PutObjectResponse response = s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            String url = getFileUrl(filename);

            Optional<PersonalDocument> personalDocumentOptional = documentRepository.findByEmailAndType(email, type);
            //create a document
            if (personalDocumentOptional.isEmpty()) {
                Optional<PersonalApplication> personalApplicationOptional = applicationRepository.findByEmail(email);
                if (personalApplicationOptional.isEmpty()) {
                    throw new NotFoundException(String.format("Application not found with email: %s", email));
                }
                PersonalApplication personalApplication = personalApplicationOptional.get();
                PersonalDocument personalDocument = new PersonalDocument();
                personalDocument.setEmail(email);
                personalDocument.setType(type);
                personalDocument.setRequired(isRequired);
                personalDocument.setPath(url);
                personalDocument.setComment("");
                personalDocument.setStatus("pending");
                personalDocument.setPersonalApplication(personalApplication);
                documentRepository.save(personalDocument);
            }
            //document exists
            else {
                PersonalDocument personalDocument = personalDocumentOptional.get();
                personalDocument.setPath(url);
                documentRepository.save(personalDocument);
            }
            return url;
        } catch (NotFoundException e) {
            throw new NotFoundException(String.format("Application not found with email: %s", email));
        } catch (Exception e) {
            throw new FailToUploadException("Failed to upload file");
        }
    }

    private String getFileUrl(String filename) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, filename);
    }
}
