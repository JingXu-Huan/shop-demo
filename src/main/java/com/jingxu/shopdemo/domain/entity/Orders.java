package com.jingxu.shopdemo.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (orders)实体类
 * @since 2025-12-08 13:33:59
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("orders")
public class Orders implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * orderId
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
	private Long orderId;
    /**
     * userId
     */
    private Long userId;
    /**
     * totalAmount
     */
    private BigDecimal totalAmount;
    /**
     * status
     */
    private Object status;
    /**
     * createdAt
     */
    private LocalDateTime createdAt;

}