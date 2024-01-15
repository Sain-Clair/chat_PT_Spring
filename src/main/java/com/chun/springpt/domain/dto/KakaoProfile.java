package com.chun.springpt.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoProfile {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date connected_at;

    private Properties properties;
    private KakaoAccount kakao_account;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {
        private String nickname;
        private String profile_image;
        private String thumbnail_image;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoAccount {
        @JsonProperty("profile_nickname_needs_agreement")
        private boolean profile_nickname_needs_agreement;

        @JsonProperty("profile_image_needs_agreement")
        private boolean profile_image_needs_agreement;

        private Profile profile;
        private boolean has_email;

        @JsonProperty("email_needs_agreement")
        private boolean email_needs_agreement;

        @JsonProperty("is_email_valid")
        private boolean is_email_valid;

        @JsonProperty("is_email_verified")
        private boolean is_email_verified;

        private String email;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Profile {
            private String nickname;

            @JsonProperty("thumbnail_image_url")
            private String thumbnail_image_url;

            @JsonProperty("profile_image_url")
            private String profile_image_url;

            @JsonProperty("is_default_image")
            private boolean is_default_image;
        }
    }
}
