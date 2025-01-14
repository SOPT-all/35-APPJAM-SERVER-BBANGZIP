package com.sopt.bbangzip.fegin.config;

import com.sopt.bbangzip.BbangzipApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackageClasses = BbangzipApplication.class)
@Configuration
public class FeignConfig {
}
