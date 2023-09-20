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

    public static <T> CommonResponse<T> error(T data) {
        return CommonResponse.<T>builder().code(500).data(data).build();
    }

    public static <T> CommonResponse<T> error(T data, String message) {
        return CommonResponse.<T>builder().code(500).data(data).message(message).build();
    }

    public static <T> CommonResponse<T> success(T data) {
        return CommonResponse.<T>builder().code(200).message("成功").data(data).build();
    }

    public static CommonResponse<Boolean> response(boolean isSuccess) {
        if (isSuccess) {
            return success(true);
        } else {
            return error(false, "失败");
        }
    }
}
