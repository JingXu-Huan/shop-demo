package com.jingxu.shopdemo.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (products)实体类
 *
 * @author kancy
 * @since 2025-12-08 13:33:59
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