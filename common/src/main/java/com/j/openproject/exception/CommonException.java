package com.j.openproject.exception;

import org.springframework.lang.NonNull;

import com.j.openproject.code.ResultCode;

/**
 * @author Joyuce
 * @Type AppException
 * @Desc 公共运行异常
 * @date 2019年11月21日
 * @Version V1.0
 */
public class CommonException extends AbstractException {

    public CommonException(@NonNull ResultCode resultCode) {
        super(resultCode);
    }
}
