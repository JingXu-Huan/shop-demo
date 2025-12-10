package com.jingxu.shopdemo.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (products)实体类
 *
 * @author kancy
 * @since 2025-12-08 13:33:59
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@TableName("products")
public class Products implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * productId
     */
    @TableId
	private Long productId;
    /**
     * name
     */
    private String name;
    /**
     * description
     */
    private String description;
    /**
     * price
     */
    private BigDecimal price;
    /**
     * stock
     */
    private Integer stock;
    /**
     * createdAt
     */
    private LocalDateTime createdAt;

}