package com.cwj.gugumall;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ThirdportApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThirdportApplication.class,args);
    }
}
