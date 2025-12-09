package com.jingxu.shopdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingxu.shopdemo.domain.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * (orders)数据Mapper
 *
 * @author kancy
 * @description 由 Mybatisplus Code Generator 创建
 * @since 2025-12-08 13:33:59
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
    @Update({
            "<script>",
            "update orders",
            "set status = '已发货'",
            "where order_id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deliveryAll(@Param("ids") List<Integer> ids);

}
