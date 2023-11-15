package com.cwj.gugumall.product;

import com.cwj.gugumall.product.entity.BrandEntity;
import com.cwj.gugumall.product.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class productApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    public void context()
    {
        BrandEntity br = new BrandEntity();
        br.setBrandId(1L);
        br.setDescript("456");
        brandService.save(br);

    }


}
