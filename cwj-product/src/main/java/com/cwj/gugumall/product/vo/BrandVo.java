package com.cwj.gugumall.product.vo;

import com.cwj.gugumall.product.entity.BrandEntity;
import lombok.Data;

@Data
public class BrandVo {
    private Long brandId;
    private String brandName;
    public BrandVo(){}
    public BrandVo(BrandEntity brandEntity){
        this.brandId = brandEntity.getBrandId();
        this.brandName = brandEntity.getName();
    }

}
