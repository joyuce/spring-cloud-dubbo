package com.j.openproject.exception;

import org.springframework.lang.NonNull;

import com.j.openproject.code.ResultCode;

import lombok.Data;

/**
 * @author Joyuce
 * @Type AppException
 * @Desc 基类异常
 * @date 2019年11月21日
 * @Version V1.0
 */
@Data
public abstract class AbstractException extends RuntimeException{

    private static final long serialVersionUID = -5483717109655912251L;

    /**
     * 结果码对象
     */
    private ResultCode resultCode;

    public AbstractException(@NonNull ResultCode resultCode) {
        super(resultCode.getCnMsg());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
