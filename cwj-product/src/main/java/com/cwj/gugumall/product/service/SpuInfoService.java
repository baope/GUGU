package com.cwj.gugumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cwj.common.utils.PageUtils;
import com.cwj.gugumall.product.entity.SpuInfoEntity;
import com.cwj.gugumall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 01:22:05
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo spuSaveVo);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

