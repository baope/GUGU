package com.cwj.gugumall.product.dao;

import com.cwj.gugumall.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 01:22:05
 */
@Mapper
@Component
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {
	public void deleteBatchRelation(@Param("attrAttrgroupRelation") List<AttrAttrgroupRelationEntity> attrAttrgroupRelation);
}
