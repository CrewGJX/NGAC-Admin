package com.phor.ngac.config;

import com.phor.ngac.entity.vo.responses.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ValidationControllerAdvice {
    @ExceptionHandler(value = BindException.class)
    public CommonResponse<String> handleException(BindException e) {
        log.error("controllerAdvice捕获BindException", e);
        String message = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining());
        return CommonResponse.error(message);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResponse<String> handleException(MethodArgumentNotValidException e) {
        log.error("controllerAdvice捕获MethodArgumentNotValidException", e);
        String message = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining());
        return CommonResponse.error(message);
    }

    @ExceptionHandler(value = Exception.class)
    public CommonResponse<String> handleException(Exception e) {
        log.error("controllerAdvice捕获Exception", e);
        return CommonResponse.error(String.format("%s: %s", e.getClass().getSimpleName(), e.getMessage()));
    }
}
