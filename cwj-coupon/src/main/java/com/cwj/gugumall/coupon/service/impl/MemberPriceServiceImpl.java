package com.cwj.gugumall.coupon.service.impl;

import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.Query;
import com.cwj.gugumall.coupon.dao.MemberPriceDao;
import com.cwj.gugumall.coupon.entity.MemberPriceEntity;
import com.cwj.gugumall.coupon.service.MemberPriceService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("memberPriceService")
public class MemberPriceServiceImpl extends ServiceImpl<MemberPriceDao, MemberPriceEntity> implements MemberPriceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberPriceEntity> page = this.page(
                new Query<MemberPriceEntity>().getPage(params),
                new QueryWrapper<MemberPriceEntity>()
        );

        return new PageUtils(page);
    }

}