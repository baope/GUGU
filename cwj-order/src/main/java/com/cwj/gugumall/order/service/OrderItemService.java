package com.cwj.gugumall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cwj.common.utils.PageUtils;
import com.cwj.gugumall.order.entity.OrderItemEntity;

import java.util.Map;

/**
 * 订单项信息
 *
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 18:51:01
 */
public interface OrderItemService extends IService<OrderItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

