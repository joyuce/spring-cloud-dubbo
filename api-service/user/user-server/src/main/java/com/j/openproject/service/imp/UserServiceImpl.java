package com.j.openproject.service.imp;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.Service;

import com.j.openproject.dto.UserDto;
import com.j.openproject.entity.User;
import com.j.openproject.mapper.UserMapper;
import com.j.openproject.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Joyuce
 * @Type UserService
 * @Desc
 * @date 2019年11月22日
 * @Version V1.0
 */
@Slf4j
@Service(version = "${user.service.version}")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDto getUserByToken(String token, String urlPath) {
        User u = userMapper.getTheList();
        UserDto userDto = new UserDto(u.getId(), u.getUserName());
        //throw new  NullPointerException();
        return userDto;
    }
}
