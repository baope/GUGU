package com.cwj.gugumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cwj.common.utils.PageUtils;
import com.cwj.gugumall.product.entity.BrandEntity;
import com.cwj.gugumall.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-18 00:15:08
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryBrandRelationEntity> queryRelationByBrandId(Long brandId);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);
    void saveBrand(BrandEntity brandEntity);

    void updateCategory(Long catId, String name);

    List<BrandEntity> getBrandsByCatId(Long catId);
}

