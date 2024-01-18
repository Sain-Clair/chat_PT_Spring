package com.chun.springpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpPhotoRequest {
    @JsonProperty("upphotoid")
    private int upphotoid;
}
