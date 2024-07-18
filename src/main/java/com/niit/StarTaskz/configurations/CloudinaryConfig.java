package com.niit.StarTaskz.configurations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", "dl3kjft6t",
                "api_key", "632358357517958",
                "api_secret", "QGsCdQf43FIrjAjRes9Ot0p4aGg");
        return new Cloudinary(config);
    }
}
