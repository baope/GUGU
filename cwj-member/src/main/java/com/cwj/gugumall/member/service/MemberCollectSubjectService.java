package com.cwj.gugumall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cwj.common.utils.PageUtils;
import com.cwj.gugumall.member.entity.MemberCollectSubjectEntity;

import java.util.Map;

/**
 * 会员收藏的专题活动
 *
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 18:47:12
 */
public interface MemberCollectSubjectService extends IService<MemberCollectSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

