package com.haru4cut.S3;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

// byte[] 를 multipartfile로 변환

@Component
public class ByteToMultiPartFile {
    public MultipartFile changeByte(byte[] base64, String date, int orderNum, Long userId) {

        int totalCnt = 1024;

        MultipartFile multipartFile = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(totalCnt)) {
            int offset = 0;
            while (offset < base64.length) {
                int chunk = Math.min(totalCnt, base64.length - offset);

                byte[] byteArray = new byte[chunk];
                System.arraycopy(base64, offset, byteArray, 0, chunk);

                byteArrayOutputStream.write(byteArray);
                byteArrayOutputStream.flush();

                offset += chunk;
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            StringBuilder sb = new StringBuilder();
            sb.append(userId);
            sb.append("-");
            sb.append(date);
            sb.append("-");
            sb.append(orderNum);

            multipartFile = new MockMultipartFile(sb.toString(), byteArrayInputStream.readAllBytes());

        } catch (IOException e) {
        }

        return multipartFile;
    }

}
