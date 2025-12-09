package com.jingxu.shopdemo.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/9
 */
@Data
@AllArgsConstructor
public class ItemsDto {
    String name;
    String description;
    BigDecimal price;
    Integer stock;
    LocalDateTime createdAt;
}
