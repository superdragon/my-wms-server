package com.jiaming.wms.common.controller;

import com.jiaming.wms.common.exception.ApiException;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

/**
 * @author dragon
 */
// @RestControllerAdvice 拦截 controller 层抛出的异常
@RestControllerAdvice
public class ExpectionAdviceHander {

    @ExceptionHandler({ApiException.class})
    public ResultVO<String> apiExceptionHandler(ApiException e) {
        if (e.getCode() != 0) {
            return new ResultVO<>(e.getCode(), e.getMsg(), e.getDetail());
        }
        return new ResultVO<>(ResultCodeEnum.BIZ_FAIL, e.getDetail());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResultVO<String> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException e) {
        // 从异常对象中拿到 ObjectError 对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        String errorMessage = objectError.getDefaultMessage();
        // 返回错误码，并在 data 中存放具体参数校验失败的原因
        return new ResultVO<>(ResultCodeEnum.FAIL_PARAM_VALIDATE, errorMessage);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResultVO<String> missingServletRequestParameterExceptionHandler(
            MissingServletRequestParameterException e) {
        return new ResultVO<>(ResultCodeEnum.FAIL_PARAM_MISSING, e.getLocalizedMessage());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResultVO<String> httpRequestMethodNotSupportedExceptionHandler(
            HttpRequestMethodNotSupportedException e) {
        return new ResultVO<>(ResultCodeEnum.BIZ_FAIL, e.getLocalizedMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResultVO<String> httpMessageNotReadableExceptionHandler(
            HttpMessageNotReadableException e) {
        return new ResultVO<>(ResultCodeEnum.BIZ_FAIL, e.getLocalizedMessage());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResultVO<String> methodArgumentTypeMismatchExceptionHandler(
            MethodArgumentTypeMismatchException e) {
        return new ResultVO<>(ResultCodeEnum.BIZ_FAIL, e.getLocalizedMessage());
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResultVO<String> httpMediaTypeNotSupportedExceptionHandler(
            HttpMediaTypeNotSupportedException e) {
        return new ResultVO<>(ResultCodeEnum.BIZ_FAIL, e.getLocalizedMessage());
    }

    @ExceptionHandler({MultipartException.class})
    public ResultVO<String> multipartExceptionHandler(
            MultipartException e) {
        return new ResultVO<>(ResultCodeEnum.BIZ_FAIL, e.getLocalizedMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResultVO<String> exceptionHandler(Exception e) {
        e.printStackTrace();
        return new ResultVO<>(ResultCodeEnum.BIZ_FAIL, e.getLocalizedMessage());
    }
}
