package com.jingxu.shopdemo.domain.vo;

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
public class ItemVO {
    Integer produceId;
    String produceName;
    String description;
    Integer quantity;
    BigDecimal money;
}
