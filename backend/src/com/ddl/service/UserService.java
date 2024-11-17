package com.ddl.service;

import com.ddl.common.StatusCode;
import com.ddl.entity.User;
import com.ddl.mapper.UserMapper;
import com.mybatisflex.core.MybatisFlexBootstrap;
import com.mybatisflex.core.query.QueryWrapper;

public class UserService {
    UserMapper userMapper = MybatisFlexBootstrap.getInstance().getMapper(UserMapper.class);

    public static UserService getInstance() {
        return new UserService();
    }

    public StatusCode register(String username, String password) {
        if(userMapper.selectCountByQuery(new QueryWrapper().eq("username", username)) != 0){
            return StatusCode.REGISTER_USERNAME_DUPLICATE;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password); //TODO：加盐
        userMapper.insert(user);
        return StatusCode.REGISTER_SUCCESS;
    }

    public StatusCode login(String username, String password) {
        User user = userMapper.selectOneByQuery(new QueryWrapper().eq("username", username));
        if(user == null || !user.getPassword().equals(password)){//TODO：这里需要加盐处理
            return StatusCode.LOGIN_FAIL;
        }else{
            return StatusCode.LOGIN_SUCCESS;
        }
    }
}