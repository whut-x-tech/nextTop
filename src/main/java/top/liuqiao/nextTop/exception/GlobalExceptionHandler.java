package top.liuqiao.nextTop.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.oxm.ValidationFailureException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import top.liuqiao.nextTop.common.BaseResponse;
import top.liuqiao.nextTop.common.ErrorCode;
import top.liuqiao.nextTop.common.ResultUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public BaseResponse<?> bindExceptionHandler(BindException e) {
        log.error("bindException", e);
        final List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();
        final StringBuilder builder = new StringBuilder(64);
        for (FieldError fieldError : fieldErrorList) {
            builder.append(fieldError.getField());
            builder.append(":");
            builder.append(fieldError.getRejectedValue());
            builder.append(" 参数错误\n");
        }
        return ResultUtils.error(ErrorCode.PARAMS_ERROR, builder.toString());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public BaseResponse<?> validationExceptionHandler(HandlerMethodValidationException e) {
        log.error("validationException", e);
        final List<ParameterValidationResult> parameterValidationResultList = e.getAllValidationResults();
        final StringBuilder builder = new StringBuilder(64);
        for (ParameterValidationResult parameterValidationResult : parameterValidationResultList) {
            for (MessageSourceResolvable resolvableError : parameterValidationResult.getResolvableErrors()) {
                builder.append(resolvableError.getDefaultMessage());
                builder.append("\n");
            }
        }
        return ResultUtils.error(ErrorCode.PARAMS_ERROR, builder.toString());
    }

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统错误");
    }
}
