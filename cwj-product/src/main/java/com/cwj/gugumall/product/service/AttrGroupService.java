package com.cwj.gugumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cwj.common.utils.PageUtils;
import com.cwj.gugumall.product.entity.AttrAttrgroupRelationEntity;
import com.cwj.gugumall.product.entity.AttrGroupEntity;

import java.util.Map;

/**
 * 属性分组
 *
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 01:22:05
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);
    public PageUtils queryPage(Map<String, Object> params,Long catelogId);

    void deleteRelation(AttrAttrgroupRelationEntity[] attrAttrgroupRelationEntity);
}

