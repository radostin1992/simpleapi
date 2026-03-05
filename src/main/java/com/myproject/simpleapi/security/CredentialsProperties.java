package com.myproject.simpleapi.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "simpleapi.credentials")
public class CredentialsProperties {

    private Auth internal;
    private Auth appapi;
    private Auth swagger;

    @Getter
    @Setter
    public static class Auth {
        private String username;
        private String password;
    }
}