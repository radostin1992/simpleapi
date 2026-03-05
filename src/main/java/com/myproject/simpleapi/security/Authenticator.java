package com.myproject.simpleapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class Authenticator {

    @Autowired
    private final CredentialsProperties credentials;

    public Authenticator(CredentialsProperties credentials) {
        this.credentials = credentials;
    }

    public HttpHeaders getAuthenticatedHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(
                credentials.getInternal().getUsername(),
                credentials.getInternal().getPassword()
        );

        return headers;
    }
}
