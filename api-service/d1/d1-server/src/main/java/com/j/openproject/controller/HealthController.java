package com.j.openproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Joyuce
 * @Type HealthController
 * @Desc 健康检查接口
 * @date 2020年01月17日
 * @Version V1.0
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}
