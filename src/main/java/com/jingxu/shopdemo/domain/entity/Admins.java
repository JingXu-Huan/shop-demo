package com.jingxu.shopdemo.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (admins)实体类
 * @since 2025-12-08 13:33:59
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("admins")
public class Admins extends Model<Admins> implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * adminId
     */
    @TableId
	private Integer adminId;
    /**
     * username
     */
    private String username;
    /**
     * password
     */
    private String password;
    /**
     * createdAt
     */
    private LocalDateTime createdAt;

}