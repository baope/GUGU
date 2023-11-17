package com.cwj.gugumall.coupon.dao;

import com.cwj.gugumall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 02:36:17
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
