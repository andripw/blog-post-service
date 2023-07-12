package com.example.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = -1530382100294395772L;

    private int statusCode;
    private boolean success;
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonIgnoreProperties({"pageable", "sort"})
    private T data;

    public static <T> BaseResponse<T> ok(T data) {
        return BaseResponse.<T>builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK.name())
                .success(true)
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> unauthorized(String message) {
        return BaseResponse.<T>builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .status(HttpStatus.UNAUTHORIZED.name())
                .success(false)
                .message(message)
                .data(null)
                .build();
    }
}
