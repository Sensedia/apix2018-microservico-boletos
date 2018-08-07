package com.sensedia.boletos.infra.swagger;

import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("api-boletos")
        .useDefaultResponseMessages(false)
        .globalResponseMessage(RequestMethod.GET, responseMessageList())
        .globalResponseMessage(RequestMethod.PUT, responseMessageList())
        .globalResponseMessage(RequestMethod.POST, responseMessageList())
        .globalResponseMessage(RequestMethod.DELETE, responseMessageList())
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
        .paths(PathSelectors.any())
        .build();
  }

  private List<ResponseMessage> responseMessageList() {
    List<ResponseMessage> response = new ArrayList<>();
    response.add(new ResponseMessageBuilder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message("Bad Request")
        .build());
    response.add(new ResponseMessageBuilder()
        .code(HttpStatus.PRECONDITION_FAILED.value())
        .message("Precondition Failed")
        .build());
    response.add(new ResponseMessageBuilder()
        .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
        .message("Unprocessable entity")
        .build());
    response.add(new ResponseMessageBuilder()
        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message("Internal Server Error")
        .build());
    return response;
  }

}
