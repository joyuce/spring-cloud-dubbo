package com.j.openproject.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.j.openproject.base.CommonRs;
import com.j.openproject.code.ResultCode;

import lombok.extern.slf4j.Slf4j;

/**
 * @author shenxiaodong
 * @Type HttpRequestUtil
 * @Desc 请求处理工具类
 * @date 2019年08月27日
 * @Version V1.0
 */
@Slf4j
public class HttpRequestUtil {

    private static final String UNKNOWN = "unknown";

    private static final String INDEX = ",";

    public static String getBodyContent(HttpServletRequest request) throws IOException {
        return getStrByStream(request.getInputStream());
    }

    public static String getStrByStream(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void setResponse(HttpServletResponse httpResp, ResultCode resultCode) throws IOException {
        httpResp.setStatus(200);
        httpResp.setContentType("application/json; charset=utf-8");
        String s = JSONObject.toJSONString(CommonRs.createWithCode(resultCode));
        httpResp.getWriter().print(s);
    }

    /**
     * 获取当前请求的用户的IP
     *
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
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