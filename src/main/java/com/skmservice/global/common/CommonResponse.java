package com.skmservice.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"statusCode", "message", "content"})
public class CommonResponse<T> {

    @JsonProperty("statusCode")
    @NonNull
    private final String statusCode;

    @JsonProperty("message")
    @NonNull
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("content")
    private T content;

    // 성공한 경우 응답 생성
    public static <T> CommonResponse<T> onSuccess(T content) {
        return new CommonResponse<>(HttpStatus.OK.name(), HttpStatus.OK.getReasonPhrase(), content);
    }

    public static <T> CommonResponse<T> onSuccess() {
        return new CommonResponse<>(HttpStatus.OK.name(), HttpStatus.OK.getReasonPhrase(), null);
    }

    // 실패한 경우 응답 생성
    public static <T> CommonResponse<T> onFailure(String statusCode, String message) {
        return new CommonResponse<>(statusCode, message, null);
    }

    public static <T> CommonResponse<T> onFailure(String statusCode, String message, T content) {
        return new CommonResponse<>(statusCode, message, content);
    }

    // Json serialize
    public String toJsonString() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
