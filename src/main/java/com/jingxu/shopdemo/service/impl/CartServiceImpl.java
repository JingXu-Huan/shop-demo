package com.jingxu.shopdemo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jingxu.shopdemo.domain.dto.CartItemDto;
import com.jingxu.shopdemo.domain.entity.CartItems;
import com.jingxu.shopdemo.domain.vo.CartVO;
import com.jingxu.shopdemo.domain.vo.Result;
import com.jingxu.shopdemo.exception.BusinessException;
import com.jingxu.shopdemo.exception.FailCodeEnums;
import com.jingxu.shopdemo.mapper.CartItemsMapper;
import com.jingxu.shopdemo.service.CartService;
import com.jingxu.shopdemo.service.ProductService;
import com.jingxu.shopdemo.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jingxu
 * @version 1.0.0
 * @since 2025/12/9
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl extends ServiceImpl<CartItemsMapper, CartItems> implements CartService {
    private final ProductService productService;

    @Override
    public Result addCartItems(CartItemDto cartItemDto) {
        Integer userId = UserContext.get();
        if (cartItemDto != null && userId != null) {
            Integer productId = cartItemDto.getProductId();
            Integer quantity = cartItemDto.getQuantity();
            CartItems cartItems = new CartItems()
                    .setQuantity(quantity)
                    .setAddedAt(LocalDateTime.now())
                    .setProductId(productId)
                    .setUserId(userId);
            try {
                this.save(cartItems);
            } catch (DuplicateKeyException e) {
                throw new BusinessException(FailCodeEnums.CART_ITEM_EXISTS, "商品已经存在于购物车");
            }
            return Result.ok("商品添加至购物车成功");
        }
        return Result.fail("商品添加失败");
    }

    @Override
    public List<CartVO> queryAllCartItem() {
        Integer userId = UserContext.get();
        if (userId != null) {
            List<CartVO> listVo = new ArrayList<>();
            List<CartItems> cartItems = this.lambdaQuery().eq(CartItems::getUserId, userId).list();
            cartItems.forEach(item -> {
                CartVO cartVO = new CartVO();
                Integer productId = item.getProductId();
                LocalDateTime addedAt = item.getAddedAt();
                Integer quantity = item.getQuantity();
                String name = productService.findName(productId);
                cartVO.setName(name);
                cartVO.setAdded_at(addedAt);
                cartVO.setQuantity(quantity);
                listVo.add(cartVO);
            });
            return listVo;
        }
        return new ArrayList<>();
    }
}
