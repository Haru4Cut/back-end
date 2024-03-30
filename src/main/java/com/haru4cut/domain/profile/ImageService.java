package com.haru4cut.domain.profile;

import com.haru4cut.domain.Character.CharacterService;
import com.haru4cut.domain.profile.dalle.ProfileService;
import com.haru4cut.domain.s3.S3ProfileUploader;
import com.haru4cut.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ProfileService profileService;
    private final CharacterService characterService;
    private final S3ProfileUploader s3ProfileUploader;
    private String dummyPrompt = "강아지와 산책하는 사람 그려줘";

    public String generateProfileImage(ImageRequestDto imageRequestDto, Long userId) {

        byte[] bytes = profileService.generatePicture(dummyPrompt);
        MultipartFile multipartFile = changeByteToMultipartFile(bytes, userId);

        String imgUri;
        try {
            imgUri = s3ProfileUploader.saveFile(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imgUri;
    }

    public MultipartFile changeByteToMultipartFile(byte[] base64, Long userId) {
        int totalCnt = 1024;
        MultipartFile multipartFile = null;

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(totalCnt)) {
            int offset = 0;

            while (offset < base64.length) {
                int chunkSize = Math.min(totalCnt,base64.length - offset);

                byte[] byteArray = new byte[chunkSize];
                System.arraycopy(base64, offset, byteArray, 0, chunkSize);

                byteArrayOutputStream.write(byteArray);
                byteArrayOutputStream.flush();

                offset += chunkSize;
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

            multipartFile = new MockMultipartFile(String.valueOf(userId), byteArrayInputStream.readAllBytes());

        } catch (IOException e) {
        }

        return multipartFile;
    }



}