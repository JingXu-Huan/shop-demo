package com.jingxu.shopdemo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jingxu.shopdemo.domain.dto.ProductDto;
import com.jingxu.shopdemo.domain.dto.ProductListDto;
import com.jingxu.shopdemo.domain.entity.Products;
import com.jingxu.shopdemo.domain.vo.ItemVO;
import com.jingxu.shopdemo.domain.vo.Result;

import java.util.List;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/8
 */
public interface ProductService extends IService<Products> {

    Result orderItems(ProductDto productDto);

    Result orderItemsByList(ProductListDto productListDto);

    String findName(Integer productId);

    List<ItemVO> queryAllItem(int pageNum,int pageSize);
}
