package com.jingxu.shopdemo.domain.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
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
    @JsonAlias({"product_id","productId"})
    Integer productId;
    Integer quantity;
}
