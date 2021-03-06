package com.nrkt.springbootcrud.config;

import com.fasterxml.classmate.TypeResolver;
import com.nrkt.springbootcrud.dto.request.PersonDtoRequest;
import com.nrkt.springbootcrud.model.Person;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nrkt.springbootcrud.controller.rest"))
                .paths(PathSelectors.regex("/api.*"))
                .build()
                .apiInfo(apiEndpointInfo())
                .globalRequestParameters(parameters())
                .useDefaultResponseMessages(false)
                .tags(new Tag("persons", "Person Service"));
    }

    private ApiInfo apiEndpointInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot Person CRUD REST API")
                .description("CRUD DEMO")
                .contact(new Contact("Nrkt", "", ""))
                .version("1.0.0")
                .build();
    }

    private List<RequestParameter> parameters() {
        RequestParameterBuilder mediaTypeBuilder = new RequestParameterBuilder();

        var requestParameter = mediaTypeBuilder
                .name("mediaType")
                .description("Enter media type: xml,yaml or hal. (hal = application/hal+json)")
                .required(false)
                .in("query")
                .query(q -> {
                    q.model(m -> m.scalarModel(ScalarType.STRING));
                    q.defaultValue("hal");
                })
                .build();

        List<RequestParameter> params = new ArrayList<>();
        params.add(requestParameter);
        return params;
    }
}
