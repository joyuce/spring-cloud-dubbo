package com.j.openproject.service;

import com.j.openproject.dto.UserDto;

/**
 * @author Joyuce
 * @Type UserService
 * @Desc
 * @date 2019年11月22日
 * @Version V1.0
 */
public interface UserService {


    public UserDto getUserByToken(String token, String urlPath);
}
