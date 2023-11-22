package com.cwj.gugumall.product.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cwj.common.to.SkuReductionTo;
import com.cwj.common.to.SpuBoundTo;
import com.cwj.gugumall.product.entity.*;
import com.cwj.gugumall.product.feigh.CouponFeigh;
import com.cwj.gugumall.product.service.*;
import com.cwj.gugumall.product.vo.*;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.Query;

import com.cwj.gugumall.product.dao.SpuInfoDao;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    SpuInfoDescService spuInfoDescService;

    @Autowired
    SpuImagesService spuImagesService;

    @Autowired
    AttrService attrService;

    @Autowired
    ProductAttrValueService attrValueService;

    @Autowired
    SkuInfoService skuInfoService;

    @Autowired
    SkuImagesService skuImagesService;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    CouponFeigh couponFeigh;


    public PageUtils queryPageByCondition(Map<String, Object> params)
    {
        QueryWrapper<SpuInfoEntity> queryWrapper = new QueryWrapper<SpuInfoEntity>();
        String key = (String)params.get("key");
        String status = (String)params.get("status");
        String brandId = (String)params.get("brandId");
        String catelogId = (String)params.get("catelogId");

        if (!StringUtils.isEmpty(catelogId)&& !"0".equalsIgnoreCase(catelogId))
        {
            queryWrapper.eq("catalog_id",catelogId);
        }

        if(!StringUtils.isEmpty(brandId)&& !"0".equalsIgnoreCase(brandId))
        {
            queryWrapper.eq("brand_id",brandId);
        }

        if(!StringUtils.isEmpty(key))
        {
            queryWrapper.and(
                    (w)->{
                        w.eq("id",key).or().like("spu_name",key);
                    }
            );
        }


        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    /**
     * 保存spu，sku，会员的信息
     * spu -> *sku
     * @param spuSaveVo
     */
    public void saveSpuInfo(SpuSaveVo spuSaveVo)
    {
        /**
         * 保存spu的基本信息
         */
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuSaveVo,spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.saveBaseSpuInfo(spuInfoEntity);

        /**
         * 保存描述图片
         */
        List<String> descript = spuSaveVo.getDecript();
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(spuInfoEntity.getId());
        descEntity.setDecript(String.join(",",descript));
        spuInfoDescService.saveSpuInfoDesc(descEntity);

        /**
         * 保存图片集合
         */
        List<String> images = spuSaveVo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(),images);

        /**
         * 保存属性
         */
        List<BaseAttrs> baseAttrs = spuSaveVo.getBaseAttrs();
        List<ProductAttrValueEntity> productAttrValueEntityList = baseAttrs.stream().map(
                attr ->{
                    ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
                    valueEntity.setAttrId(attr.getAttrId());
                    AttrEntity id = attrService.getById(attr.getAttrId());
                    valueEntity.setAttrName(id.getAttrName());
                    valueEntity.setAttrValue(attr.getAttrValues());
                    valueEntity.setQuickShow(attr.getShowDesc());
                    valueEntity.setSpuId(spuInfoEntity.getId());
                    return valueEntity;
                }
        ).collect(Collectors.toList());
        attrValueService.saveProductAttr(productAttrValueEntityList);

        List<Skus> skus = spuSaveVo.getSkus();


        /**
         * 保存会员信息
         */
        Bounds bounds = spuSaveVo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds,spuBoundTo);
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        couponFeigh.save(spuBoundTo);

        skus.forEach(item-> {
            String defaultImg = "";
            for (Images image : item.getImages()) {
                if (image.getDefaultImg() == 1) {
                    defaultImg = image.getImgUrl();
                }
            }
            SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
            BeanUtils.copyProperties(item, skuInfoEntity);
            skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
            skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
            skuInfoEntity.setSaleCount(0L);
            skuInfoEntity.setSpuId(spuInfoEntity.getId());
            skuInfoEntity.setSkuDefaultImg(defaultImg);
            
            //5.1）、sku的基本信息；pms_sku_info
            skuInfoService.saveSkuInfo(skuInfoEntity);
            Long skuId = skuInfoEntity.getSkuId();


            //5.2）、sku的图片信息；pms_sku_image
            List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img -> {
                SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                skuImagesEntity.setSkuId(skuId);
                skuImagesEntity.setImgUrl(img.getImgUrl());
                skuImagesEntity.setDefaultImg(img.getDefaultImg());
                return skuImagesEntity;
            }).filter(entity -> {
                //返回true就是需要，false就是剔除
                return !StringUtils.isEmpty(entity.getImgUrl());
            }).collect(Collectors.toList());
            skuImagesService.saveBatch(imagesEntities);

            //5.3）、sku的销售属性信息：pms_sku_sale_attr_value
            List<Attr> attr = item.getAttr();
            List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(a -> {
                SkuSaleAttrValueEntity attrValueEntity = new SkuSaleAttrValueEntity();
                BeanUtils.copyProperties(a, attrValueEntity);
                attrValueEntity.setSkuId(skuId);
                return attrValueEntity;
            }).collect(Collectors.toList());
            skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

            SkuReductionTo skuReductionTo =new SkuReductionTo();
            BeanUtils.copyProperties(item,skuReductionTo);
            skuReductionTo.setSkuId(skuId);
            couponFeigh.saveSkuReduction(skuReductionTo);


        });
    }

    private void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity) {
        this.baseMapper.insert(spuInfoEntity);
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

}