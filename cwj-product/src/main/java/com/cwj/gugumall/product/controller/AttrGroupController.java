package com.cwj.gugumall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.cwj.gugumall.product.entity.AttrAttrgroupRelationEntity;
import com.cwj.gugumall.product.entity.AttrEntity;
import com.cwj.gugumall.product.service.AttrAttrgroupRelationService;
import com.cwj.gugumall.product.service.AttrService;
import com.cwj.gugumall.product.service.CategoryService;
import com.cwj.gugumall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cwj.gugumall.product.entity.AttrGroupEntity;
import com.cwj.gugumall.product.service.AttrGroupService;
import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.R;



/**
 * 属性分组
 *
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 01:22:05
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 查询类内所有分组中的所有属性
     * @param catlogId
     * @return
     */
    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupAttrs(@PathVariable("catelogId") Long catlogId){
        List<AttrGroupWithAttrsVo> vos = attrGroupService.getAttrGroupWithAttrsByCatlogId(catlogId);
        return R.ok().put("data",vos);
    }


    /**
     * 添加attr和attrgroup关系
     * @param attrAttrgroupRelationEntities
     * @return
     */
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities){
        attrAttrgroupRelationService.addRelation(attrAttrgroupRelationEntities);
        return R.ok();
    }

    /**
     * 获取组中没有的属性
     * @param attrgroupId
     * @param parms
     * @return
     */
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrgroupId") Long attrgroupId,
                            @RequestParam Map<String,Object> parms)
    {
        PageUtils page = attrService.getNoRelationAttr(attrgroupId,parms);
        return R.ok().put("page",page);
    }


    /**
     * 删除关联表中的属性组中的属性
     * @param attrAttrgroupRelationEntity
     * @return
     */
    @PostMapping("attr/relation/delete")
    public R deleteRelation(@RequestBody AttrAttrgroupRelationEntity[] attrAttrgroupRelationEntity)
    {
        attrGroupService.deleteRelation(attrAttrgroupRelationEntity);
        return R.ok();
    }

    /**
     * 显示属性组里的属性
     * @param attrgroupId
     * @return
     */
    @GetMapping("{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId){
        List<AttrEntity> entityList = attrService.getRelationAttr(attrgroupId);
        return R.ok().put("data",entityList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params,@PathVariable("catelogId") Long catelogId){
        PageUtils page = attrGroupService.queryPage(params,catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
		Long[] Parent = categoryService.findCatelogParent(attrGroup.getCatelogId());
        attrGroup.setCatelogPath(Parent);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
