package com.cwj.gugumall.coupon.service.impl;

import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.Query;
import com.cwj.gugumall.coupon.dao.HomeSubjectSpuDao;
import com.cwj.gugumall.coupon.entity.HomeSubjectSpuEntity;
import com.cwj.gugumall.coupon.service.HomeSubjectSpuService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("homeSubjectSpuService")
public class HomeSubjectSpuServiceImpl extends ServiceImpl<HomeSubjectSpuDao, HomeSubjectSpuEntity> implements HomeSubjectSpuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HomeSubjectSpuEntity> page = this.page(
                new Query<HomeSubjectSpuEntity>().getPage(params),
                new QueryWrapper<HomeSubjectSpuEntity>()
        );

        return new PageUtils(page);
    }

}