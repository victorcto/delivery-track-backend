package br.com.deliverytrack.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    private static final String[] ALLOWED_METHODS = {"GET", "OPTIONS", "POST", "PATCH", "DELETE", "PUT"};

    private static final String[] ALLOWED_HEADERS = {"Accept", "Referer", "User-Agent", "Authorization", "X-TenantID",
            "Origin", "X-Requested-With", "Content-Type", "Content-Disposition", "Sec-Fetch-Mode", "x-ijt"};

    private static final String[] EXPOSED_HEADERS = {"Content-Disposition"};

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**")
                .allowedOrigins("*")
                .allowedMethods(ALLOWED_METHODS)
                .allowedHeaders(ALLOWED_HEADERS)
                .exposedHeaders(EXPOSED_HEADERS)
                .allowCredentials(false)
                .maxAge(3600);
    }

}
