package com.cwj.gugumall.product.feigh;


import com.cwj.common.to.SkuReductionTo;
import com.cwj.common.to.SpuBoundTo;
import com.cwj.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("coupon")
public interface CouponFeigh {
    @RequestMapping("coupon/spubounds/save")
    public R save(@RequestBody SpuBoundTo spuBounds);

    @PostMapping("coupon/skufullreduction/saveinfo")
    public R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
