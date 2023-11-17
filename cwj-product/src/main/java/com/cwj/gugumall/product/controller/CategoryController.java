package com.cwj.gugumall.product.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cwj.gugumall.product.entity.CategoryEntity;
import com.cwj.gugumall.product.service.CategoryService;
import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.R;



/**
 * 商品三级分类
 *
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 01:22:05
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list/tree")
    public R list(@RequestParam Map<String, Object> params){
        List<CategoryEntity> entities = categoryService.listWithTree();

        List<CategoryEntity> entity =  entities.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == 0;
        }).map((menu)->{
            getChildrens(menu,entities);
            return menu;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort() == null? 0:menu1.getSort()) - (menu2.getSort() == null? 0:menu2.getSort());
        }).collect(Collectors.toList());

        return R.ok().put("data", entity);
    }

    public void getChildrens(CategoryEntity root,List<CategoryEntity> all)
    {
        root.setChildren(
                all.stream().filter((categoryEntity)->{
                    return categoryEntity.getParentCid() == root.getCatId();
                }).map((menu)->{
                    getChildrens(menu,all);
                    return menu;
                }).sorted((menu1,menu2)->{
                    return (menu1.getSort() == null? 0:menu1.getSort()) - (menu2.getSort() == null? 0:menu2.getSort());
                }).collect(Collectors.toList())
        );
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.saveCascade(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R delete(@RequestBody Long[] catIds){
        System.out.println(Arrays.toString(catIds));

		categoryService.removeMenuByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
