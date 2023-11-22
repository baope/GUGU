package com.cwj.gugumall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.cwj.gugumall.product.entity.ProductAttrValueEntity;
import com.cwj.gugumall.product.service.ProductAttrValueService;
import com.cwj.gugumall.product.vo.AttrrespVo;
import com.cwj.gugumall.product.vo.Attrvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @PostMapping("/update/{spuId}")
    public R updateSpuAttr(@PathVariable("spuId") Long spuId,
                            @RequestBody List<ProductAttrValueEntity> entities)
    {
        productAttrValueService.updateBatchsByspuId(spuId,entities);
        return R.ok();
    }


    /**
     * 获取spu的属性
     * Request URL: http://localhost:88/api/product/attr/base/listforspu/13
     * @param spuId
     * @return
     */
    @GetMapping("/base/listforspu/{spuid}")
    public R baseAttrlistforspu(@PathVariable("spuid") Long spuId)
    {
        List<ProductAttrValueEntity> entityList = productAttrValueService.baseAttrListforSpu(spuId);
        return R.ok().put("data",entityList);
    }

    /**
     * 查询列表，显示表的所有数据
     * @param params
     * @param catelogId
     * @param type
     * @return
     */
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
