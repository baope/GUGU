package com.cwj.gugumall.product.dao;

import com.cwj.gugumall.product.entity.CommentReplayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价回复关系
 * 
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-18 00:15:08
 */
@Mapper
public interface CommentReplayDao extends BaseMapper<CommentReplayEntity> {
	
}
