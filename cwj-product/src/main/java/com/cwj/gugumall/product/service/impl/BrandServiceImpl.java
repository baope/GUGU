package com.cwj.gugumall.product.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.cwj.gugumall.product.service.CategoryBrandRelationService;
import com.cwj.gugumall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.Query;

import com.cwj.gugumall.product.dao.BrandDao;
import com.cwj.gugumall.product.entity.BrandEntity;
import com.cwj.gugumall.product.service.BrandService;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    public void updateDetail(BrandEntity brand){
        this.baseMapper.updateById(brand);
        if(brand.getName() != "")
        {
            categoryBrandRelationService.saveBrand(brand);
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> queryWrapper = new QueryWrapper<BrandEntity>();
        if(!StringUtil.isBlank(key))
        {
            queryWrapper.eq("brand_id",key).or().like("name",key);
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}