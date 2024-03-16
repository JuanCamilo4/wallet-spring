package com.siscode.wallet.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class WalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletApplication.class, args);
    }

    @Configuration
    static class ApiConfig implements WebMvcConfigurer {
        @Override
        public void configurePathMatch(PathMatchConfigurer configurer) {
            configurer.addPathPrefix("/api", c -> c.isAnnotationPresent(RestController.class));
        }
    }
}
