package com.haru4cut.domain.oauth2;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String oAuthId;
    private String name;
    private SocialType socialType;

    @Builder
    private OAuthAttributes(Map<String, Object> attributes, String oAuthId, String name, SocialType socialType) {
        this.attributes = attributes;
        this.name = name;
        this.socialType = socialType;
        this.oAuthId = oAuthId;
    }

    public static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        String oAuthId = String.valueOf(attributes.get("id"));
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String name = (String) profile.get("nickname");

        return OAuthAttributes.builder().oAuthId(oAuthId).attributes(attributes).name(name).socialType(SocialType.KAKAO).build();
    }

}
