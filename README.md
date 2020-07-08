# spring-cloud-dubbo

基于阿里组件的springcloud项目（可作为样板项目快速开发）

配置中心和注册中心：nacos（路由和限流配置均在nacos，支持动态配置）

网关：gateway

服务间调用rpc：dubbo

服务和网关集成sentinel限流，异常全局处理

网关层token和接口权限校验，服务间调用无需鉴权

集成elk日志收集

docker部署
