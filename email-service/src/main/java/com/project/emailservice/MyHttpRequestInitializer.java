package com.project.emailservice;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.client.http.HttpRequest;

import java.io.IOException;

public class MyHttpRequestInitializer implements HttpRequestInitializer {

    private final GoogleCredentials credentials;

    public MyHttpRequestInitializer(GoogleCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public void initialize(HttpRequest httpRequest) throws IOException {
        AccessToken token = credentials.refreshAccessToken();
        httpRequest.getHeaders().setAuthorization("Bearer " + token.getTokenValue());
    }

}