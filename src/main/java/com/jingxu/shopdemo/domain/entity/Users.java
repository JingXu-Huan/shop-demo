package com.jingxu.shopdemo.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (users)实体类
 *
 * @author kancy
 * @since 2025-12-08 13:33:59
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("users")
public class Users extends Model<Users> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * userId
     */
    @TableId(type = IdType.AUTO)
	private Long userId;
    /**
     * username
     */
    private String username;
    /**
     * password
     */
    private String password;
    /**
     * email
     */
    private String email;
    /**
     * createdAt
     */
    private LocalDateTime createdAt;

}