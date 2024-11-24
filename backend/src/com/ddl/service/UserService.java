package com.ddl.service;

import com.ddl.common.StatusCode;
import com.ddl.entity.User;
import com.ddl.mapper.UserMapper;
import com.mybatisflex.core.MybatisFlexBootstrap;
import com.mybatisflex.core.query.QueryWrapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class UserService {
    UserMapper userMapper = MybatisFlexBootstrap.getInstance().getMapper(UserMapper.class);

    public static UserService getInstance() {
        return new UserService();
    }

    public StatusCode register(String username, String password) {
        if(userMapper.selectCountByQuery(new QueryWrapper().eq("username", username)) != 0){
            return StatusCode.REGISTER_USERNAME_DUPLICATE;
        }

        String salt =generateSalt();
        String hashedPassword = hashSHA256(password + salt);

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword); //TODO：加盐
        user.setSalt(salt);
        userMapper.insert(user);
        return StatusCode.REGISTER_SUCCESS;
    }

    public StatusCode login(String username, String password) {
        User user = userMapper.selectOneByQuery(new QueryWrapper().eq("username", username));
        String hashedPassword = hashSHA256(password + user.getSalt());
        if(user == null || !user.getPassword().equals(hashedPassword)){//TODO：这里需要加盐处理
            return StatusCode.LOGIN_FAIL;
        }else{
            return StatusCode.LOGIN_SUCCESS;
        }
    }

    private String generateSalt() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}