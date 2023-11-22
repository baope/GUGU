package com.cwj.gugumall.product.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cwj.gugumall.product.dao.AttrAttrgroupRelationDao;
import com.cwj.gugumall.product.entity.AttrAttrgroupRelationEntity;
import com.cwj.gugumall.product.entity.AttrEntity;
import com.cwj.gugumall.product.service.AttrService;
import com.cwj.gugumall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.Query;

import com.cwj.gugumall.product.dao.AttrGroupDao;
import com.cwj.gugumall.product.entity.AttrGroupEntity;
import com.cwj.gugumall.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    AttrService attrService;

    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatlogId(Long catlogId)
    {
        List<AttrGroupEntity> attrGroupEntityList = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id",catlogId));
        List<AttrGroupWithAttrsVo> attrGroupWithAttrsVoList = attrGroupEntityList.stream().map(
                (obj)->{
                    List<AttrEntity> attrEntity = attrService.getRelationAttr(obj.getAttrGroupId());
                    AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
                    BeanUtils.copyProperties(obj,attrGroupWithAttrsVo);
                    attrGroupWithAttrsVo.setAttrs(attrEntity);
                    return attrGroupWithAttrsVo;
                }
        ).collect(Collectors.toList());
        return attrGroupWithAttrsVoList;
    }


    /**
     * 批量删除关联
     * @param attrAttrgroupRelationEntity
     */
    public void deleteRelation(AttrAttrgroupRelationEntity[] attrAttrgroupRelationEntity)
    {
        attrAttrgroupRelationDao.deleteBatchRelation(Arrays.asList(attrAttrgroupRelationEntity));
        return;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    public PageUtils queryPage(Map<String, Object> params,Long catelogId) {
        IPage iPage = null;
        QueryWrapper<AttrGroupEntity> queryWrapper = null;
        String key = (String)params.get("key");
        if(catelogId == 0){
            queryWrapper = new QueryWrapper<AttrGroupEntity>();
        }
        else {
            queryWrapper = new QueryWrapper<AttrGroupEntity>()
                    .eq("catelog_id", catelogId);
        }
        if(!StringUtil.isBlank(key))
        {
            queryWrapper.and(
                    (obj)->{
                        obj.or().like("attr_group_name",key);
                    }
            );
        }
        iPage = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(iPage);
    }

}