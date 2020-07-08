package com.j.openproject.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.j.openproject.thread.FlexThreadPool;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Joyuce
 * @Type BaseController
 * @Desc 基础控制器
 * @date 2019年11月28日
 * @Version V1.0
 */
@Slf4j
public abstract class BaseController {

    /** 异步处理线程池 */
    protected FlexThreadPool taskPool = new FlexThreadPool(8);

    private final String UNKNOWN = "unknown";

    private final String INDEX = ",";

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public String getIpAddress() {
        return getIpAddr(getRequest());
    }

    public Integer getUserId() {
        return (Integer) getRequest().getAttribute("userId");
    }

    /**
     * 获取当前请求的用户的IP
     *
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.error("ip获取发生异常", e);
        }
        //使用代理，则获取第一个IP地址
        if (StringUtils.isNotEmpty(ip) && ip.length() > 15) {
            if (ip.indexOf(INDEX) > 0) {
                ip = ip.substring(0, ip.indexOf(INDEX));
            }
        }
        return ip;
    }

}
