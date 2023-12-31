package com.cwj.gugumall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.Query;

import com.cwj.gugumall.product.dao.ProductAttrValueDao;
import com.cwj.gugumall.product.entity.ProductAttrValueEntity;
import com.cwj.gugumall.product.service.ProductAttrValueService;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {


    public void updateBatchsByspuId(Long spuId, List<ProductAttrValueEntity> entities)
    {
        this.baseMapper.delete(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id",spuId));
        entities.forEach(
                (obj)->{
                    obj.setSpuId(spuId);
                }
        );
        this.saveOrUpdateBatch(entities);
    }


    public List<ProductAttrValueEntity> baseAttrListforSpu(Long spuId)
    {
        return this.baseMapper.selectList(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id",spuId));
    }

    public void saveProductAttr(List<ProductAttrValueEntity> productAttrValueEntityList)
    {
        this.saveBatch(productAttrValueEntityList);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

}