package com.jingxu.shopdemo.domain.vo;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/9
 */
@Data
public class CartVO {
    String name;
    Integer product_id;
    Integer quantity;
    LocalDateTime added_at;
}
