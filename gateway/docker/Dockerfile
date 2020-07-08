#引用镜像 默认时区 中时区 需要安装才能更改时区
#apk add -U tzdata
#cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
FROM openjdk:8-jdk-alpine
VOLUME /tmp
MAINTAINER j
ENV LANG C.UTF-8
ADD gateway-0.0.1.jar app.jar
#暴露容器端口 和web开放端口对应
EXPOSE 18085
# 更新时间
RUN touch /app.jar
#-Djava.security.egd=file:/dev/./urandom 加快随机数生成
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]