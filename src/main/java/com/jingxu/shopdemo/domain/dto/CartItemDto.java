package com.jingxu.shopdemo.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/9
 */
@Data
@AllArgsConstructor
public class CartItemDto {
    Integer productId;
    Integer quantity;
}
