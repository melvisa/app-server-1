package com.app.hupi.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger文档配置<br>
 * http://ip:port/contextPath/swagger-ui.html<br>
 * http://ip:port/contextPath/docs.html<br>
 * http://ip:port/contextPath/doc.html<br>
 * http://ip:port/contextPath/swagger-editor.html<br>
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan("com.hn.kite.controller")
public class SwaggerConfig {

    /**
     */
    private boolean swaggerEnabled=true;

    /**
     * 创建Swagger文档
     * 
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .enable(swaggerEnabled)
            .apiInfo(apiInfo())
            .groupName(Docket.DEFAULT_GROUP_NAME)
            .genericModelSubstitutes(DeferredResult.class)
            .useDefaultResponseMessages(false)
            .forCodeGeneration(false)
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .paths(PathSelectors.any())
            .build();
    }

    /**
     * Swagger文档信息
     * 
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("kite-web").version("1.0").build();
    }
}
