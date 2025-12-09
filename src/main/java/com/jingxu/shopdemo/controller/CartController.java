package com.jingxu.shopdemo.controller;


import com.jingxu.shopdemo.domain.dto.CartItemDto;
import com.jingxu.shopdemo.domain.vo.CartVO;
import com.jingxu.shopdemo.domain.vo.Result;
import com.jingxu.shopdemo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/9
 */
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**用户将商品添加至购物车*/
    @PutMapping("/add")
    public Result addCartItems(@RequestBody CartItemDto cartItemDto) {
        return cartService.addCartItems(cartItemDto);
    }

    /**用户浏览购物车内的商品*/
    @GetMapping("/queryAll")
    public List<CartVO> queryAllCartItem(){
        return cartService.queryAllCartItem();
    }


}
