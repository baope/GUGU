package com.cwj.gugumall.product.controller;

import java.util.Arrays;
import java.util.Map;


import com.cwj.gugumall.product.vo.AttrrespVo;
import com.cwj.gugumall.product.vo.Attrvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwj.gugumall.product.entity.AttrEntity;
import com.cwj.gugumall.product.service.AttrService;
import com.cwj.common.utils.PageUtils;
import com.cwj.common.utils.R;



/**
 * 商品属性
 *
 * @author cwj
 * @email baope.ans@gmail.com
 * @date 2023-11-14 01:22:05
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * 查询列表，显示表的所有数据
     * @param params
     * @param catelogId
     * @param type
     * @return
     */
    // /base & /sale
    @RequestMapping("/{attrType}/list/{catelogId}")
    public R baselist(@RequestParam Map<String, Object> params,@PathVariable Long catelogId,
                      @PathVariable("attrType")  String type){
        PageUtils page = attrService.queryBaseAttrPage(params,catelogId,type);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
        AttrrespVo attrrespVo = attrService.getAttrrespVo(attrId);
        return R.ok().put("attr", attrrespVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody Attrvo attr){
		attrService.saveAttr(attr);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody Attrvo attr){
		attrService.updateAttrvo(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
