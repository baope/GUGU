package com.cwj.gugumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cwj.common.utils.PageUtils;
import com.cwj.gugumall.product.entity.AttrEntity;
import com.cwj.gugumall.product.vo.AttrrespVo;
import com.cwj.gugumall.product.vo.Attrvo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 01:22:05
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(Attrvo attr);

    PageUtils  queryBaseAttrPage(Map<String, Object> params, Long catelogId,String type);

    AttrrespVo getAttrrespVo(Long attrId);

    void updateAttrvo(Attrvo attr);
    List<AttrEntity> getRelationAttr(Long attrgroupId);

    PageUtils getNoRelationAttr(Long attrgroupId,Map<String,Object> parms);
}

