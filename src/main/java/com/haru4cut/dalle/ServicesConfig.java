package com.haru4cut.dalle;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

@Configuration
public class ServicesConfig {
    @Value("${openai.key}")
    private String apiKey;

    @Bean
    @Primary
    public OpenAiService getOpenAiService(){
        return new OpenAiService(apiKey, Duration.ofSeconds(30));
    }
}
