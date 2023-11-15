package com.cwj.gugumall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cwj.common.utils.PageUtils;
import com.cwj.gugumall.order.entity.OrderReturnReasonEntity;

import java.util.Map;

/**
 * 退货原因
 *
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 18:51:01
 */
public interface OrderReturnReasonService extends IService<OrderReturnReasonEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

