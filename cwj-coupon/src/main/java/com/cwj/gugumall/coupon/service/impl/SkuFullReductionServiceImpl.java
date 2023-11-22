package com.cwj.gugumall.coupon.service.impl;

import com.cwj.common.to.MemberPrice;
import com.cwj.common.to.SkuReductionTo;
import com.cwj.gugumall.coupon.entity.MemberPriceEntity;
import com.cwj.gugumall.coupon.entity.SkuLadderEntity;
import com.cwj.gugumall.coupon.service.MemberPriceService;
import com.cwj.gugumall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.Query;

import com.cwj.gugumall.coupon.dao.SkuFullReductionDao;
import com.cwj.gugumall.coupon.entity.SkuFullReductionEntity;
import com.cwj.gugumall.coupon.service.SkuFullReductionService;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Autowired
    SkuLadderService skuLadderService;

    @Autowired
    MemberPriceService memberPriceService;


    public void saveSkuRuction(SkuReductionTo skuReductionTo)
    {
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        BeanUtils.copyProperties(skuReductionTo,skuLadderEntity);
        skuLadderEntity.setAddOther(skuReductionTo.getCountStatus());
        skuLadderService.save(skuLadderEntity);

        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTo,skuFullReductionEntity);
        this.save(skuFullReductionEntity);

        List<MemberPrice> memberPriceList = skuReductionTo.getMemberPrice();

        List<MemberPriceEntity> memberPriceEntityList = memberPriceList.stream().map(
                obj->{
                    MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
                    memberPriceEntity.setSkuId(memberPriceEntity.getSkuId());
                    memberPriceEntity.setAddOther(1);
                    memberPriceEntity.setMemberLevelId(obj.getId());
                    memberPriceEntity.setMemberLevelName(obj.getName());
                    memberPriceEntity.setMemberPrice(obj.getPrice());
                    return memberPriceEntity;
                }
        ).filter(item->{
            return item.getMemberPrice().compareTo(new BigDecimal(0)) == 1;
        }).collect(Collectors.toList());

        memberPriceService.saveBatch(memberPriceEntityList);
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

}