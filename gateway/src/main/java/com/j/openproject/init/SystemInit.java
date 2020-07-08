package com.j.openproject.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.j.openproject.route.RefreshRouteByNacos;

/**
 * @author Joyuce
 * @Type SystemInit
 * @Desc
 * @date 2020年01月15日
 * @Version V1.0
 */
@Component
public class SystemInit implements CommandLineRunner {

    @Autowired
    private RefreshRouteByNacos refreshRouteByNacos;

    @Override
    public void run(String... args) throws Exception {
        //开启自动刷新路由
        refreshRouteByNacos.dynamicRouteByNacosListener();
    }
}
