package com.cwj.gugumall.product.service.impl;

import com.cwj.common.utils.R;
import com.cwj.gugumall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.Query;

import com.cwj.gugumall.product.dao.CategoryDao;
import com.cwj.gugumall.product.entity.CategoryEntity;
import com.cwj.gugumall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    public void saveCascade(CategoryEntity category){
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());

    }



    public R removeMenuByIds(List<Long> asList) {
        baseMapper.deleteBatchIds(asList);
        return R.ok();
    }

    public List<CategoryEntity> listWithTree(){
        return baseMapper.selectList(null);

    }

    public Long[] findCatelogParent(Long catelogId){
        List<Long> list = findParent(catelogId);
        return list.toArray(new Long[list.size()]);
    }

    private List<Long> findParent(Long catelogId){
        List<Long> list = new ArrayList<>();
        Long Parent = this.baseMapper.selectById(catelogId).getParentCid();
        if(Parent != 0)
        {
            list = findParent(Parent);
        }
        list.add(catelogId);
        return list;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

}