package com.j.openproject.exception;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSONObject;
import com.j.openproject.base.CommonRs;
import com.j.openproject.code.CommonRsCode;

import reactor.core.publisher.Mono;

/**
 * @author Joyuce
 * @Type SentinelExceptionHandler
 * @Desc 限流异常处理
 * @date 2020年01月14日
 * @Version V1.0
 */
public class SentinelExceptionHandler implements WebExceptionHandler {

    private List<ViewResolver> viewResolvers;
    private List<HttpMessageWriter<?>> messageWriters;

    public SentinelExceptionHandler(List<ViewResolver> viewResolvers, ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolvers;
        this.messageWriters = serverCodecConfigurer.getWriters();
    }

    private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange, Throwable ex) {
        CommonRs rs = null;
        // 不同的异常返回不同的提示语
        if (ex instanceof FlowException) {
            rs = CommonRs.createWithCode(CommonRsCode.INT_LIMIT);
        } else if (ex instanceof DegradeException) {
            rs = CommonRs.createWithCode(CommonRsCode.SER_DOWN);
        } else if (ex instanceof ParamFlowException) {
            rs = CommonRs.createWithCode(CommonRsCode.PARA_LIMIT);
        } else if (ex instanceof SystemBlockException) {
            rs = CommonRs.createWithCode(CommonRsCode.SYS_PROTECT);
        } else if (ex instanceof AuthorityException) {
            rs = CommonRs.createWithCode(CommonRsCode.AUTH_FAILED);
        } else {
            ex.printStackTrace();
            rs = new CommonRs();
            rs.setCode("500");
            rs.setCnMsg("发现了未知内部错误");
        }
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        DataBuffer buffer = serverHttpResponse.bufferFactory()
                .wrap(JSONObject.toJSONString(rs).getBytes(StandardCharsets.UTF_8));
        return serverHttpResponse.writeWith(Mono.just(buffer));
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        if (!BlockException.isBlockException(ex)) {
            return Mono.error(ex);
        }
        return handleBlockedRequest(exchange, ex).flatMap(response -> writeResponse(response, exchange, ex));
    }

    private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
        return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
    }

}
