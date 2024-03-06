package com.haru4cut.dalle;

import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class APIService {
    @Resource(name = "getOpenAiService")
    private final OpenAiService openAiService;

    public String generateOnePicture(String prompt){
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .prompt(prompt)
                .size("1024x1792")
                .model("dall-e-3")
                .style("vivid")
                .responseFormat("b64_json")
                .n(1)
                .build();
        String b64 = openAiService.createImage(createImageRequest).getData().get(0).getB64Json();
        return b64;
    }

    public String generateWidthPicture(String prompt){
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .prompt(prompt)
                .size("1792x1024")
                .model("dall-e-3")
                .style("vivid")
                .responseFormat("b64_json")
                .n(1)
                .build();
        String b64 = openAiService.createImage(createImageRequest).getData().get(0).getB64Json();
        return b64;
    }

    public String generateFourPicture(String prompt){
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .prompt(prompt)
                .size("1024x1024")
                .model("dall-e-3")
                .style("vivid")
                .responseFormat("b64_json")
                .n(1)
                .build();
        String b64 = openAiService.createImage(createImageRequest).getData().get(0).getB64Json();
        return b64;
    }

}
