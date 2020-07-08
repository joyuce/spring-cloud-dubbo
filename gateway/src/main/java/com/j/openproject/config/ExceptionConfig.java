package com.j.openproject.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import com.j.openproject.exception.CommonExceptionHandler;

/**
 * @author Joyuce
 * @Type ExceptionConfig
 * @Desc 全局异常处理配置
 * @date 2020年01月14日
 * @Version V1.0
 */
@Configuration
public class ExceptionConfig {

    /**
     * 自定义异常处理[@@]注册Bean时依赖的Bean，会从容器中直接获取，所以直接注入即可
     *
     * @param viewResolversProvider view resolvers provider
     * @param serverCodecConfigurer server codec configurer
     * @return the error web exception handler
     */
    @Primary
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider,
            ServerCodecConfigurer serverCodecConfigurer) {
        CommonExceptionHandler commonExceptionHandler = new CommonExceptionHandler();
        commonExceptionHandler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
        commonExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
        commonExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
        return commonExceptionHandler;
    }
}
