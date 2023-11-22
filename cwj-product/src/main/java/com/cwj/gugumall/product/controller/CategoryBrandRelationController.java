package com.cwj.gugumall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.cwj.gugumall.product.entity.BrandEntity;
import com.cwj.gugumall.product.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cwj.gugumall.product.entity.CategoryBrandRelationEntity;
import com.cwj.gugumall.product.service.CategoryBrandRelationService;
import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.R;



/**
 * 品牌分类关联
 *
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-18 00:15:08
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @GetMapping("/brands/list")
    public R relationBrandsList(@RequestParam(value = "catId") Long catId){
        List<BrandEntity> brandEntities = categoryBrandRelationService.getBrandsByCatId(catId);
        List<BrandVo> brandVos = brandEntities.stream().map(
                (obj)->{
                    BrandVo brandVo = new BrandVo(obj);
                    return brandVo;
                }
        ).collect(Collectors.toList());
        brandVos.forEach(obj-> System.out.println(obj.toString()));
        return R.ok().put("data",brandVos);
    }
    /**
     * 列表
     */
    @RequestMapping("catelog/list")
    public R list(@RequestParam("brandId") Long brandId){
        List<CategoryBrandRelationEntity> categoryBrandRelationEntity = categoryBrandRelationService.queryRelationByBrandId(brandId);
        return R.ok().put("data", categoryBrandRelationEntity);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.saveDetail(categoryBrandRelation);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
