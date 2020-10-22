package com.nrkt.springbootcrud.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.nrkt.springbootcrud.helper.MediaTypeYmlConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonYamlConfig {

    @Bean
    public MediaTypeYmlConverter yamlHttpMessageConverter() {

        YAMLMapper mapper = new YAMLMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return new MediaTypeYmlConverter(mapper);
    }
}
