package com.j.openproject.exhandler;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.j.openproject.base.CommonRs;
import com.j.openproject.code.CommonRsCode;
import com.j.openproject.exception.CommonException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Joyuce
 * @Type ExceptionHandler
 * @Desc 控制层异常捕获处理器
 * @date 2019年11月21日
 * @Version V1.0
 */
@Slf4j
public abstract class BaseExHandler {

    /**
     * 缺少参数异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({ MissingServletRequestParameterException.class })
    @ResponseBody
    public CommonRs requestMissingServletRequest(MissingServletRequestParameterException ex) {
        CommonRs rs = CommonRs.createWithCode(CommonRsCode.VALID_ERROR);
        rs.setData("缺少必要参数,参数名称为" + ex.getParameterName());
        return rs;
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonRs notValidExceptionHandler(MethodArgumentNotValidException e) {
        CommonRs rs = CommonRs.createWithCode(CommonRsCode.VALID_ERROR);
        rs.setData(getErrorMsg(e.getBindingResult().getAllErrors()));
        return rs;
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonRs exceptionHandler(Exception e) {
        log.error("捕获到未知Exception异常", e);
        return CommonRs.createWithCode(CommonRsCode.INT_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(value = CommonException.class)
    public CommonRs appExceptionHandler(CommonException e) {
        log.info("业务异常 code:" + e.getResultCode().getCode() + " msg:" + e.getResultCode().getCnMsg());
        return CommonRs.createWithCode(e.getResultCode());
    }

    @ResponseBody
    @ExceptionHandler(value = NullPointerException.class)
    public CommonRs nullExceptionHandler(NullPointerException e) {
        log.error("捕获到空指针异常", e);
        return CommonRs.createWithCode(CommonRsCode.INT_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public CommonRs methodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.info("前端请求方式错误 msg:" + e.getLocalizedMessage());
        return CommonRs.createWithCode(CommonRsCode.REQUEST_ERROR);
    }

    private String getErrorMsg(List<ObjectError> allErrors) {
        StringBuilder message = new StringBuilder();
        for (ObjectError error : allErrors) {
            message.append(error.getDefaultMessage()).append(" & ");
        }
        return message.substring(0, message.length() - 3);
    }

}
