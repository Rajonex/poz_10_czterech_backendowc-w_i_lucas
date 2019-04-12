package com.example.spring.OAuth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;


@Configuration
public class ResourceConfiguration extends AuthorizationServerConfigurerAdapter {

    @Bean
    public OAuth2RestTemplate restTemplate() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setAccessTokenUri("https://allegro.pl/auth/oauth/token");
        resourceDetails.setClientId("e788ff075e3c44bd8cd9da7c2a22d047");
        resourceDetails.setClientSecret("1IT4MqGV8AZonTMQjdm67YMTTTpAzAdSeKwNn3feLQkBTlnWPfcbg0yxCUipGZy1");
        resourceDetails.setGrantType("client_credentials");

        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
//        restTemplate.setMessageConverters(asList(new MappingJackson2HttpMessageConverter()));
        return restTemplate;
    }

}