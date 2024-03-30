package com.haru4cut.domain.profile.dalle;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ProfileConfig {

    @Value("${openai.key}")
    private String apiKey;

    @Bean
    @Qualifier("profileOpenAiService")
    public OpenAiService getProfileOpenAiService() {
        return new OpenAiService(apiKey, Duration.ofSeconds(60));
    }

}
