package com.fmi.hotelreviewboard.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.util.HashMap;

@Configuration
@EnableSpringDataWebSupport
public class ApplicationCloudConfiguration {
    @Value("${cloudinary.cloud-name}")
    private String cloudApiName;

    @Value("${cloudinary.api-key}")
    private String cloudApiKey;

    @Value("${cloudinary.api-secret}")
    private String cloudApiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(new HashMap<String, Object>(){{
            put("cloud_name", cloudApiName);
            put("api_key", cloudApiKey);
            put("api_secret", cloudApiSecret);
        }});
    }

}
