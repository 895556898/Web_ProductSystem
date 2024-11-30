package com.ddl.service;

import com.ddl.common.StatusCode;
import com.ddl.entity.User;
import com.ddl.entity.dto.UpdatePasswordDTO;
import com.ddl.entity.dto.UserDTO;
import com.ddl.entity.vo.CurrentUserVO;
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

        String salt = generateSalt();
        String hashedPassword = hashSHA256(password + salt);

        User user = new User();
        user.setUsername(username);
        user.setHashedPassword(hashedPassword); //TODO：加盐
        user.setSalt(salt);
        userMapper.insert(user);
        return StatusCode.REGISTER_SUCCESS;
    }

    public StatusCode login(UserDTO userDTO) {
        User user = userMapper.selectOneByQuery(new QueryWrapper().eq("username", userDTO.getUsername()));
        if(user == null){
            return StatusCode.LOGIN_FAIL;
        }

        String hashedPassword = hashSHA256(userDTO.getPassword() + user.getSalt());
        if(!user.getHashedPassword().equals(hashedPassword)){
            return StatusCode.LOGIN_FAIL;
        }else{
            return StatusCode.LOGIN_SUCCESS;
        }
    }

    // 获取当前用户信息
    public CurrentUserVO getCurrentUser(String username) {
        User user = userMapper.selectOneByQuery(new QueryWrapper().eq("username", username));
        return new CurrentUserVO(user.getUsername(), user.getAvatar(), user.getId(), user.getEmail());
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

    //用户修改密码
    public StatusCode updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        User user = userMapper.selectOneByQuery(new QueryWrapper().eq("username", updatePasswordDTO.getUsername()));
        if(user == null){
            return StatusCode.USER_NOT_FOUND;
        }

        String hashedOldPassword = hashSHA256(updatePasswordDTO.getOldPassword() + user.getSalt());
        if(!user.getHashedPassword().equals(hashedOldPassword)){
            return StatusCode.OLD_PASSWORD_INCORRECT;
        }

        String newSalt = generateSalt();
        String hashedNewPassword = hashSHA256(updatePasswordDTO.getNewPassword() + newSalt);
        user.setHashedPassword(hashedNewPassword);
        user.setSalt(newSalt);

        int updatedRows = userMapper.update(user);
        if(updatedRows > 0){
            return StatusCode.PASSWORD_UPDATE_SUCCESS;
        } else {
            return StatusCode.PASSWORD_UPDATE_FAILED;
        }
    }

    //注销账户
    public StatusCode deleteUserByUsername(String username) {
        User user = userMapper.selectOneByQuery(new QueryWrapper().eq("username", username));
        if (user == null) {
            return StatusCode.USER_NOT_FOUND;
        }

        int deletedRows = userMapper.deleteById(user.getId());
        if (deletedRows > 0) {
            return StatusCode.ACCOUNT_DELETION_SUCCESS;
        } else {
            return StatusCode.ACCOUNT_DELETION_FAILED;
        }
    }
}