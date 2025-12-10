package com.jingxu.shopdemo.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemVO {
    @JsonSerialize(using = ToStringSerializer.class)
    Long productId;
    String name;
    String description;
    Integer stock;
    BigDecimal price;
}
