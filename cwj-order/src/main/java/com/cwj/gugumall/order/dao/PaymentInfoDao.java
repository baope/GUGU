package com.cwj.gugumall.order.dao;

import com.cwj.gugumall.order.entity.PaymentInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息表
 * 
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 18:51:01
 */
@Mapper
public interface PaymentInfoDao extends BaseMapper<PaymentInfoEntity> {
	
}
