package zzc.springcloud.order.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
	
	//api接口包扫描路径
	public static final String SWAGGER_SCAN_BASE_PACKAGE = "zzc.springcloud.order.controller";
	
	public static final String VERSION = "1.0.0";
	
	@Bean
    public Docket createRestApi() {
		// 添加Header，或在每个Controller里的方法上加@ApiImplicitParams({@ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token,格式: Bearer toekn (Bearer token中间的空格必须要)", required = true) })
		List<Parameter> pars = new ArrayList<>();
		pars.add(new ParameterBuilder().name("Authorization")
			.description("token,格式: Bearer toekn (Bearer token中间的空格必须要)")
			.modelRef(new ModelRef("string"))
			.parameterType("header")
			.required(true).build()); // header中的Authorization参数是否非必填
		
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any()) // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .build()
                .globalOperationParameters(pars); // 为每个方法都添加Header
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Security集成Swagger2")  //设置文档的标题
                .description("resutful api接口列表")  // 设置文档的描述
                .version(VERSION)// 设置文档的版本信息
                .termsOfServiceUrl("http:/localhost:9003") // 设置文档的License信息
                .build();
    }
}
