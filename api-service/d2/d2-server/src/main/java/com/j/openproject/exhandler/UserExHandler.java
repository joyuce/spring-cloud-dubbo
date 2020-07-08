package com.j.openproject.exhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.j.openproject.base.CommonRs;
import com.j.openproject.code.CommonRsCode;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Joyuce
 * @Type UserExHandler
 * @Desc
 * @date 2020年01月16日
 * @Version V1.0
 */
@Slf4j
@ControllerAdvice
public class UserExHandler extends BaseExHandler {

    @ExceptionHandler(BlockException.class)
    @ResponseBody
    public CommonRs sentinelBlockHandler(BlockException e) {
        log.error("Blocked by Sentinel: {}", e.getRule());
        return CommonRs.createWithCode(CommonRsCode.INT_LIMIT);
    }

}
