package com.cwj.gugumall.product.vo;


import lombok.Data;

@Data
public class AttrrespVo  extends Attrvo{
    private String groupName;
    private String catelogName;
    private Long[] catelogPath;
}
