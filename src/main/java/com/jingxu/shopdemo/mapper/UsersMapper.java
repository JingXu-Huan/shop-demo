package com.jingxu.shopdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jingxu.shopdemo.domain.entity.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * (users)数据Mapper
 *
 * @author kancy
 * @since 2025-12-08 13:33:59
*/
@Mapper()
public interface UsersMapper extends BaseMapper<Users> {

}
