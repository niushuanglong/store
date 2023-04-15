package com.niu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class DataTableApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DataTableApplication.class, args);
    }
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DataTableApplication.class);
    }
}
