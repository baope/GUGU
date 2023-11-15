package com.cwj.gugumall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cwj.common.utils.PageUtils;
import com.cwj.gugumall.member.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 会员等级
 *
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 18:47:12
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

