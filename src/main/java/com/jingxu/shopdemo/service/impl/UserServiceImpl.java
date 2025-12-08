package com.jingxu.shopdemo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingxu.shopdemo.domain.dto.UserDto;
import com.jingxu.shopdemo.domain.entity.Users;
import com.jingxu.shopdemo.domain.vo.Result;
import com.jingxu.shopdemo.mapper.UsersMapper;
import com.jingxu.shopdemo.service.UserService;
import com.jingxu.shopdemo.utils.UserContext;
import com.jingxu.shopdemo.utils.Utils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UsersMapper, Users> implements UserService {
    private final UsersMapper usersMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Result singUp(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        Users one = this.lambdaQuery().select(Users::getUsername).one();
        if (one != null) {
            return Result.fail("用户已经注册");
        }
        LocalDateTime creatAt = LocalDateTime.now();
        Users users = new Users();
        BeanUtils.copyProperties(userDto, users);
        users.setCreatedAt(creatAt);
        try {
            usersMapper.insert(users);
        } catch (Exception e) {
            log.error("插入失败{}", userDto.getUsername());
            throw new RuntimeException(e);
        }
        return Result.ok("用户注册成功" + userDto.getUsername());
    }

    @Override
    public Result singIn(UserDto userDto,HttpSession session) {
        if (userDto == null) {
            return null;
        }
        String username = userDto.getUsername();
        String password = userDto.getPassword();

        Users users = this.lambdaQuery().eq(Users::getUsername, username).one();
        if (users != null) {
            if (users.getPassword().equals(password)) {
                Integer userId = users.getUserId();
                UserContext.set(userId);
                session.setAttribute("userId",userId);
                Integer count = jdbcTemplate.queryForObject(Utils.querySql, Integer.class,userId);
                //如果是首次登录,新增登陆表
                if (count==null||count==0){
                    jdbcTemplate.update(Utils.insertSql,userId,true);
                }
                jdbcTemplate.update(Utils.updateSql, userId);
            }

            return Result.ok("用户" + username + "登录成功", 1);
        }
        return Result.fail("登录失败");
    }
}
