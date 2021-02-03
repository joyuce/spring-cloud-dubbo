package com.j.openproject.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSONObject;
import com.j.openproject.base.CommonRs;
import com.j.openproject.dto.UserDto;
import com.j.openproject.service.UserService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Joyuce
 * @Type AuthFilter
 * @Desc 权限校验过滤器
 * @date 2020年01月16日
 * @Version V1.0
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    /**
     * 排序字段
     */
    private final int order = 0;

    @Reference(version = "${user.service.version}")
    private UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        final String requestUri = serverHttpRequest.getURI().getPath();
        log.info(requestUri);
        String token = serverHttpRequest.getHeaders().getFirst("token");
        if (StringUtils.isBlank(token)) {
            return setFailedRequest(HttpStatus.BAD_REQUEST, exchange);
        }
        UserDto userDto = userService.getUserByToken(token, requestUri);
        if (userDto == null) {
            return setFailedRequest(HttpStatus.UNAUTHORIZED, exchange);
        }
        ServerHttpRequest host = null;
        try {
            host = exchange.getRequest().mutate().
                    header("userId", userDto.getUserId().toString())
                    .header("userName", URLEncoder.encode(userDto.getUserName(), "UTF-8")).build();
        } catch (UnsupportedEncodingException e) {
            log.error("请求头增加用户参数异常", e);
            return setFailedRequest(HttpStatus.UNAUTHORIZED, exchange);
        }
        //将现在的request 变成 change对象
        ServerWebExchange build = exchange.mutate().request(host).build();
        return chain.filter(build);
    }

    /**
     * 请求失败
     */
    private Mono<Void> setFailedRequest(HttpStatus status, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        CommonRs authResponse = new CommonRs<>(null, String.valueOf(status.value()), status.getReasonPhrase(),
                status.getReasonPhrase());
        return exchange.getResponse()
                .writeWith(Flux.just(response.bufferFactory().wrap(JSONObject.toJSONString(authResponse).getBytes())));
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
