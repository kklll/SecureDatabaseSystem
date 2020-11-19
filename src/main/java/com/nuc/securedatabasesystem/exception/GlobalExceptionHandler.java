package com.nuc.securedatabasesystem.exception;

import com.nuc.securedatabasesystem.config.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * @Author DeepBlue
 * @Date 2020/11/11 19:16
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 捕捉自定义异常，减少代码量
     *
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public ResultBody handleCustomException(HttpServletRequest request, CustomException exception) {
        log.error("发生异常");
        return ResultBody.error(exception.getErrorCode(), exception.getMessage());
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResultBody exceptionHandler(HttpServletRequest req, NullPointerException e) {
        log.error("发生空指针异常！原因是:", e);
        return ResultBody.error(CommonEnum.BODY_NOT_MATCH);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResultBody constraintViolationExceptionHandle(HttpServletRequest req, ConstraintViolationException e) {
        String message = e.getMessage();
        String[] messages = message.split(": ");
        return ResultBody.error(messages[messages.length - 1]);
    }
}
