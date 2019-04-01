package cn.voidnet.todolist;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi(ServletContext servletContext) {
        ResponseMessage message200=new ResponseMessageBuilder()
                .code(200)
                .message("成功! ^_^ ")
                //.responseModel(new ModelRef("Error"))
                .build();
        ResponseMessage message500=new ResponseMessageBuilder()
                                .code(500)
                                .message("服务器出了点儿问题QAQ跟用户说是网络异常就行~(>_<)~")
                                //.responseModel(new ModelRef("Error"))
                                .build();
        ResponseMessage message503=new ResponseMessageBuilder()
                .code(503)
                .message("请求方式有误")
                //.responseModel(new ModelRef("Error"))
                .build();
        ResponseMessage message400=new ResponseMessageBuilder()
                .code(400)
                .message("参数错误或者参数传递方式有误")
                //.responseModel(new ModelRef("Error"))
                .build();
        ResponseMessage message404=new ResponseMessageBuilder()
                .code(404)
                .message("这个应该不用解释了QAQ 找不到请求的资(dui)源(xiang)")
                //.responseModel(new ModelRef("Error"))
                .build();
        List<ResponseMessage> messages=
                new ArrayList<>(List.of(message200,message500,message400,message404,message503));
        return new Docket(DocumentationType.SWAGGER_2)
                .host("localhost:8080")
                .pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
                        return "/api";
                    }
                })
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET,messages)
                .globalResponseMessage(RequestMethod.POST,messages)
                .globalResponseMessage(RequestMethod.PUT,messages)
                .globalResponseMessage(RequestMethod.DELETE,messages)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.voidnet.todolist"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("一个待办事项程序的API文档")
                .description("提供TodoList的增删查改API   作者:虚空")
                .version("2.33")
                .build();
    }

}

