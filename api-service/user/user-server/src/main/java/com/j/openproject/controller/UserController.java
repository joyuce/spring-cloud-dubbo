package com.j.openproject.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.j.openproject.annotation.RestPathController;
import com.j.openproject.base.CommonRs;
import com.j.openproject.dto.UserDto;
import com.j.openproject.service.UserService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Joyuce
 * @Type UserController
 * @Desc 用户接口
 * @date 2020年01月16日
 * @Version V1.0
 */
@Api(value = "用户接口")
@Slf4j
@RestPathController("/api/v2/user")
public class UserController extends BaseController {

    @Reference(version = "${user.service.version}")
    private UserService userService;

    @GetMapping("/check/token")
    public CommonRs<UserDto> getUserByToken(
            @RequestParam(value = "token") String token, @RequestParam(value = "urlPath") String urlPath
    ) {
        return CommonRs.createSuccessRs(userService.getUserByToken(token, urlPath));
    }

}
