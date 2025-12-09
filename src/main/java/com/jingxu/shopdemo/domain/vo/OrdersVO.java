package com.jingxu.shopdemo.domain.vo;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/9
 */
@Data
public class OrdersVO {
    Integer orderId;
    BigDecimal total;
    String status;
    LocalDateTime orderedTime;
    //商品列表
    List<ItemVO> ItemsList;
}
