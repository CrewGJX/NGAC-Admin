package com.phor.ngac.entity.vo.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse<T> {
    private Integer code;

    private String message;

    private T data;

    public static CommonResponse<String> error(String message) {
        return CommonResponse.<String>builder().code(500).message(message).build();
    }

    public static <T> CommonResponse<T> success(T data) {
        return CommonResponse.<T>builder().code(200).message("成功").data(data).build();
    }
}
