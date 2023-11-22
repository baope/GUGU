package com.cwj.gugumall.ware.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.Query;

import com.cwj.gugumall.ware.dao.PurchaseDetailDao;
import com.cwj.gugumall.ware.entity.PurchaseDetailEntity;
import com.cwj.gugumall.ware.service.PurchaseDetailService;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public List<PurchaseDetailEntity> listDetailByPurchaseId(Long id) {

        List<PurchaseDetailEntity> purchaseId = this.list(new QueryWrapper<PurchaseDetailEntity>().eq("purchase_id", id));

        return purchaseId;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<PurchaseDetailEntity> lambdaQueryWrapper = new LambdaQueryWrapper<PurchaseDetailEntity>();

        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            lambdaQueryWrapper.and(
                    w->{
                        w.eq(PurchaseDetailEntity::getId,key).or().like(PurchaseDetailEntity::getSkuId,key);
                    }
            );
        }

        String status = (String) params.get("status");
        if(!StringUtils.isEmpty(status)){
            lambdaQueryWrapper.eq(PurchaseDetailEntity::getStatus,status);
        }

        String wareId = (String) params.get("wareId");
        if(!StringUtils.isEmpty(wareId)){
            lambdaQueryWrapper.eq(PurchaseDetailEntity::getWareId,wareId);
        }

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                lambdaQueryWrapper
        );

        return new PageUtils(page);
    }

}