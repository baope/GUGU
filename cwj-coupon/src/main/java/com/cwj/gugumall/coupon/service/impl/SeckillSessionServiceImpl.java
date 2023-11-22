package com.cwj.gugumall.coupon.service.impl;

import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.Query;
import com.cwj.gugumall.coupon.dao.SeckillSessionDao;
import com.cwj.gugumall.coupon.entity.SeckillSessionEntity;
import com.cwj.gugumall.coupon.service.SeckillSessionService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("seckillSessionService")
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSessionEntity> implements SeckillSessionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SeckillSessionEntity> page = this.page(
                new Query<SeckillSessionEntity>().getPage(params),
                new QueryWrapper<SeckillSessionEntity>()
        );

        return new PageUtils(page);
    }

}