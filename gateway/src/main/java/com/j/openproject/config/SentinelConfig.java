package com.j.openproject.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import com.j.openproject.exception.SentinelExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Joyuce
 * @Type SentinelConfig
 * @Desc 限流配置
 * @date 2020年01月14日
 * @Version V1.0
 */
@Slf4j
@Configuration
public class SentinelConfig {

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public SentinelConfig(ObjectProvider<List<ViewResolver>> viewResolversProvider,
            ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    /**
     * 配置SentinelExceptionHandler 限流后异常处理
     *
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelExceptionHandler sentinelExceptionHandler() {
        return new SentinelExceptionHandler(viewResolvers, serverCodecConfigurer);
    }
}
