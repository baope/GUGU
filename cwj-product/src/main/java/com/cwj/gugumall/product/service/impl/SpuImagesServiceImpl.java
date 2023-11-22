package com.cwj.gugumall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.Query;

import com.cwj.gugumall.product.dao.SpuImagesDao;
import com.cwj.gugumall.product.entity.SpuImagesEntity;
import com.cwj.gugumall.product.service.SpuImagesService;


@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {

    public void saveImages(Long id, List<String> images)
    {
        if(images != null && !images.isEmpty())
        {
            List<SpuImagesEntity> spuImagesEntityList = new ArrayList<>();
            images.forEach(
                (obj)->{
                    SpuImagesEntity imagesEntity = new SpuImagesEntity();
                    imagesEntity.setSpuId(id);
                    imagesEntity.setImgUrl(obj);
                    spuImagesEntityList.add(imagesEntity);
                }
            );
            this.saveBatch(spuImagesEntityList);
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

}