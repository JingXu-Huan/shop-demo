package com.jingxu.shopdemo.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (order_items)实体类
 *
 * @author kancy
 * @since 2025-12-08 13:33:59
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("order_items")
public class OrderItems extends Model<OrderItems> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * orderItemId
     */
    @TableId
	private Integer orderItemId;
    /**
     * orderId
     */
    private Integer orderId;
    /**
     * productId
     */
    private Integer productId;
    /**
     * quantity
     */
    private Integer quantity;
    /**
     * price
     */
    private BigDecimal price;

}