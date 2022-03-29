package com.bs.demo.config;

import com.bs.demo.common.Common;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * Created on 2021/12/28
 * swagger配置
 *
 * @author gf
 * @description RedisConfig
 */
@Configuration
@EnableSwagger2 //开启swagger2
@EnableSwaggerBootstrapUI
//http://localhost:8080/swagger-ui.html#/
    //访问地址(http://localhost:8080/doc.html)
public class SwaggerConfig {

    @Bean   //配置docket以配置Swagger具体参数
    public Docket docket(Environment environment) {

        return new Docket(DocumentationType.SWAGGER_2)
                //.groupName("")
                .apiInfo(apiInfo())
                .enable(true) //配置是否启用Swagger，如果是false，在浏览器将无法访问
                .select()   // 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.bs.demo"))
                .build();
    }

    //配置文档信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact(Common.AUTHOR, Common.GITEE, Common.EMAIL);
        return new ApiInfo(
                Common.NAME, // 标题
                Common.DESCRIPTION, // 描述
                Common.VERSION, // 版本
                Common.URL, // 组织链接
                contact, // 联系人信息
                "许可", // 许可
                "许可连接", // 许可连接
                new ArrayList<>()// 扩展
        );
    }

}
