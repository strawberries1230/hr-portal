package com.project.applicationservice.Config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;
import com.amazonaws.services.rds.auth.GetIamAuthTokenRequest;
import com.amazonaws.services.rds.auth.RdsIamAuthTokenGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RDSConfig {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;
    @Value("${aws.rds.region}")
    private String region;

    @Value("${aws.rds.endpoint}")
    private String endpoint;

//    @Value("${aws.rds.username}")
//    private String username;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;
    @Bean
    public String rdsAuthToken() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);

        RdsIamAuthTokenGenerator generator = RdsIamAuthTokenGenerator.builder()
                .credentials(new AWSStaticCredentialsProvider(awsCreds))
                .region(region)
                .build();

        return generator.getAuthToken(
                GetIamAuthTokenRequest.builder()
                        .hostname(endpoint)
                        .port(3306)
                        .userName(username)
                        .build());
    }
    @Bean
    public DataSource dataSource(String rdsAuthToken) {
        return DataSourceBuilder.create()
                .url(jdbcUrl)
                .username(username)
                .password(rdsAuthToken)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}

