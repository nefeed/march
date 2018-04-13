package io.nefeed.march.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @author 章华隽
 * @mail nefeed@163.com
 * @time 2018-04-13 16:24
 */
@ControllerAdvice
class ExceptionhandlerController {
    private static Logger LOG = LoggerFactory.getLogger(ExceptionhandlerController.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String exception(Exception exception) {
        LOG.error("raised exception : {}", exception);

        return exception.getMessage();
    }

    /**
     * 处理实体字段校验不通过异常
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String validationError(MethodArgumentNotValidException ex) {
        LOG.error("raised MethodArgumentNotValidException : {}", ex);
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder builder = new StringBuilder();

        for (FieldError error : fieldErrors) {
            builder.append( error.getDefaultMessage()+"\n");
        }
        return builder.toString();
    }


}
