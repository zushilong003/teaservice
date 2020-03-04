package com.lingtian.pmrApi;


import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
@SpringBootApplication//项目启动注解
@MapperScan("com.lingtian.pmrApi.model.dao")
@ServletComponentScan
public class PmrApiApplication {
    private static final Logger log = LoggerFactory.getLogger(PmrApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PmrApiApplication.class, args);
        log.info("**************teaserver-load-finished************");
    }

    /**
     * 文件上传配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("30MB"); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize("30MB");
        return factory.createMultipartConfig();
    }
}
