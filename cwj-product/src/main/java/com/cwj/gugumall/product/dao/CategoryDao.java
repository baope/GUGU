package com.cwj.gugumall.product.dao;

import com.cwj.gugumall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 01:22:05
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
