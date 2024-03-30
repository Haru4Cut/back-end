package com.haru4cut.domain.profile.dalle;


import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final OpenAiService openAiService;

    @Autowired
    public ProfileService(@Qualifier("profileOpenAiService") OpenAiService openAiService) {
        this.openAiService = openAiService;
    }


    public byte[] generatePicture(String prompt) {

        String b64Json = openAiService.createImage(CreateImageRequest.builder()
                .prompt(prompt)
                .model("dall-e-3")
                .style("vivid")
                .responseFormat("b64_json")
                .n(1)
                .build()).getData().get(0).getB64Json();

        byte[] imageByte = Base64.decodeBase64(b64Json);

        return imageByte;
    }


}
