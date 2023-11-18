package com.cwj.gugumall.product.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cwj.common.constant.AuthServerConstant;
import com.cwj.common.constant.ProductConstant;
import com.cwj.gugumall.product.dao.AttrAttrgroupRelationDao;
import com.cwj.gugumall.product.entity.*;
import com.cwj.gugumall.product.service.AttrAttrgroupRelationService;
import com.cwj.gugumall.product.service.AttrGroupService;
import com.cwj.gugumall.product.service.CategoryService;
import com.cwj.gugumall.product.vo.AttrrespVo;
import com.cwj.gugumall.product.vo.Attrvo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.Query;

import com.cwj.gugumall.product.dao.AttrDao;
import com.cwj.gugumall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    AttrGroupService attrGroupService;

    @Autowired
    CategoryService categoryService;

//    @Autowired
//    AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    /**
     * 获取属性分组中的属性
     * @param attrgroupId
     * @return
     */
    public List<AttrEntity> getRelationAttr(Long attrgroupId)
    {
        List<AttrAttrgroupRelationEntity> list = attrAttrgroupRelationService.
                list(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id",attrgroupId));
        List<Long> attrIdlist = list.stream().map(
                (obj)->{
                    return obj.getAttrId();
                }
        ).collect(Collectors.toList());
        if(attrIdlist.size() == 0)
        {
            return null;
        }
        List<AttrEntity> attrEntityList = listByIds(attrIdlist);
        return attrEntityList;
    }



    public void updateAttrvo(Attrvo attr)
    {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.saveOrUpdate(attrEntity);

        /**
         * 修改联系集
         */

        Long attrGroupId = attr.getAttrGroupId();
        Long attrId = attr.getAttrId();
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrId(attrId);
        attrAttrgroupRelationEntity.setAttrGroupId(attrGroupId);
        attrAttrgroupRelationService.saveOrUpdate(attrAttrgroupRelationEntity,
                new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attrId)
                );
    }

    public AttrrespVo getAttrrespVo(Long attrId)
    {
        AttrEntity attrEntity = this.getById(attrId);
        AttrrespVo attrrespVo = new AttrrespVo();
        BeanUtils.copyProperties(attrEntity,attrrespVo);
        Long[] Path = categoryService.findCatelogParent(attrEntity.getCatelogId());

        CategoryEntity categoryEntity = categoryService.getById(attrEntity.getCatelogId());

        attrrespVo.setCatelogPath(Path);

        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity =  attrAttrgroupRelationService.getOne(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attrId)
        );

        if(attrAttrgroupRelationEntity != null )
        {
            attrrespVo.setAttrGroupId(attrAttrgroupRelationEntity.getAttrGroupId());
            AttrGroupEntity attrGroupEntity = attrGroupService.getOne(
                    new QueryWrapper<AttrGroupEntity>().eq("attr_group_id",attrAttrgroupRelationEntity.getAttrGroupId())
            );
            attrrespVo.setGroupName(
                    attrGroupEntity == null ? null:attrGroupEntity.getAttrGroupName()
            );
        }
        attrrespVo.setCatelogName(
                categoryEntity == null? null: categoryEntity.getName()
        );
        return attrrespVo;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }


    @Transactional
    public void saveAttr(Attrvo attr){
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.save(attrEntity);
        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode())
        {
            return;
        }
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
        attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
//        attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        attrAttrgroupRelationService.save(attrAttrgroupRelationEntity);
    }


    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type)
    {
        String key = (String) params.get("key");
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type"
                ,"base".equalsIgnoreCase(type)?1:0);
        if(!StringUtil.isBlank(key))
        {
            queryWrapper.eq("attr_id",key).or().like("attr_name",key);
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );

        List<AttrEntity> list = page.getRecords();

        PageUtils pageUtils = new PageUtils(page);

        List<AttrrespVo> l =list.stream().map(
                (obj) -> {
                    AttrrespVo attrrespVo = new AttrrespVo();
                    BeanUtils.copyProperties(obj,attrrespVo);

                    AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationService.getOne(
                                    new QueryWrapper<AttrAttrgroupRelationEntity>()
                                            .eq("attr_id",attrrespVo.getAttrId())
                    );

                    if(attrAttrgroupRelationEntity != null)
                    {
                        AttrGroupEntity attrGroupEntity = attrGroupService.getOne(
                                new QueryWrapper<AttrGroupEntity>()
                                        .eq("attr_group_id",attrAttrgroupRelationEntity.getAttrGroupId())
                        );
                        if(attrGroupEntity != null)
                        {
                            attrrespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                        }
                    }

                    CategoryEntity categoryEntity = categoryService.getOne(
                            new QueryWrapper<CategoryEntity>().eq("cat_id",obj.getCatelogId())
                    );
                    if(categoryEntity != null)
                    {
                        attrrespVo.setCatelogName(categoryEntity.getName());
                    }
                    return attrrespVo;
                }
        ).collect(Collectors.toList());

        pageUtils.setList(l);

        return pageUtils;
    }



}