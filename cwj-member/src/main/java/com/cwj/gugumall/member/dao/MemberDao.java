package com.cwj.gugumall.member.dao;

import com.cwj.gugumall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 18:47:12
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
