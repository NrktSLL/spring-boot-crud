package com.nrkt.springbootcrud.config;

import com.nrkt.springbootcrud.mapper.PersonDtoMap;
import com.nrkt.springbootcrud.mapper.PersonMap;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Lazy
public class ModelMapperConfig {

    @Bean(name = "customModelMapper")
    public ModelMapper customModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.addMappings(new PersonMap());
        modelMapper.addMappings(new PersonDtoMap());
        return modelMapper;
    }

    @Bean(name = "modelMapper")
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }
}
