package com.jingxu.shopdemo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jingxu.shopdemo.domain.dto.CartItemDto;
import com.jingxu.shopdemo.domain.entity.CartItems;
import com.jingxu.shopdemo.domain.vo.CartVO;
import com.jingxu.shopdemo.domain.vo.Result;

import java.util.List;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/9
 */

public interface CartService extends IService<CartItems> {
    Result addCartItems(CartItemDto cartItemDto);

    List<CartVO> queryAllCartItem();
}
