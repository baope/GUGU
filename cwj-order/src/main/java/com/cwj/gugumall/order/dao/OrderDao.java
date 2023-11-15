package com.cwj.gugumall.order.dao;

import com.cwj.gugumall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 18:51:01
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
