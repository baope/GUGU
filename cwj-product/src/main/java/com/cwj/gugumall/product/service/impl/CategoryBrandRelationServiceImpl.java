package com.cwj.gugumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cwj.gugumall.product.entity.BrandEntity;
import com.cwj.gugumall.product.service.BrandService;
import com.cwj.gugumall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.Query;

import com.cwj.gugumall.product.dao.CategoryBrandRelationDao;
import com.cwj.gugumall.product.entity.CategoryBrandRelationEntity;
import com.cwj.gugumall.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryBrandRelationEntity> queryRelationByBrandId(Long brandId)
    {
        LambdaQueryWrapper<CategoryBrandRelationEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CategoryBrandRelationEntity::getBrandId,brandId);
        return this.baseMapper.selectList(lambdaQueryWrapper);
    }


    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation){
        Long BrandId = categoryBrandRelation.getBrandId();
        Long CatelogId = categoryBrandRelation.getCatelogId();
        String categoryName = categoryService.getById(CatelogId).getName();
        String BrandName = brandService.getById(BrandId).getName();
        categoryBrandRelation.setBrandName(BrandName);
        categoryBrandRelation.setCatelogName(categoryName);
        this.save(categoryBrandRelation);
    }

    public void saveBrand(BrandEntity brandEntity)
    {
        Long BranId = brandEntity.getBrandId();
        String BrandName = brandEntity.getName();
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setBrandName(BrandName);
        categoryBrandRelationEntity.setBrandId(BranId);
        this.update(categoryBrandRelationEntity,new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id",BranId));
    }

    public void updateCategory(Long catId, String name)
    {
        this.baseMapper.updateCategory(name,catId);
    }


}