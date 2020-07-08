package com.j.openproject.route;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


/**
 * @author Joyuce
 * @Type RefreshRouteByNacos
 * @Desc 路由刷新
 * @date 2020年01月14日
 * @Version V1.0
 */
@Slf4j
@Component
public class RefreshRouteByNacos implements ApplicationEventPublisherAware {

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String address;

    @Value("${gateway.nacos.dataid}")
    private String dataId;

    @Value("${gateway.nacos.group}")
    private String group;


    private static final List<String> ROUTE_LIST = new ArrayList<>();

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }


    /**
     * 刷新路由
     */
    public void refreshRoute() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }


    public void dynamicRouteByNacosListener() {
        try {
            ConfigService configService = NacosFactory.createConfigService(address);
            String config = configService.getConfig(dataId, group, 5000);
            synRouteByNacosConfig(config);
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    synRouteByNacosConfig(configInfo);
                }
                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步路由
     *
     * @param configInfo
     */
    private void synRouteByNacosConfig(String configInfo){
        try {
            List<RouteDefinition> gatewayRouteDefinitions = JSONObject
                    .parseArray(configInfo, RouteDefinition.class);
            clearRoute();
            for (RouteDefinition routeDefinition : gatewayRouteDefinitions) {
                addRoute(routeDefinition);
            }
            refreshRoute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void clearRoute() {
        for(String id : ROUTE_LIST) {
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
        }
        ROUTE_LIST.clear();
    }

    private void addRoute(RouteDefinition definition) {
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            ROUTE_LIST.add(definition.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}