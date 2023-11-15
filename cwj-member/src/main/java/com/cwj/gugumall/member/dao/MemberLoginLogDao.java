package com.cwj.gugumall.member.dao;

import com.cwj.gugumall.member.entity.MemberLoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员登录记录
 * 
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 18:47:12
 */
@Mapper
public interface MemberLoginLogDao extends BaseMapper<MemberLoginLogEntity> {
	
}
