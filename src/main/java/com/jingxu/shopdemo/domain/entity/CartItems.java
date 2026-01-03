package com.jingxu.shopdemo.domain.entity;

import java.io.Serial;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (cart_items)实体类
 *
 * @since 2025-12-08 13:33:59
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("cart_items")
public class CartItems implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * cartItemId
     */
    @TableId(type = IdType.AUTO)
    private Long cartItemId;
    /**
     * userId
     */
    private Long userId;
    /**
     * productId
     */
    private Long productId;
    /**
     * quantity
     */
    private Integer quantity;
    /**
     * addedAt
     */
    private LocalDateTime addedAt;

}