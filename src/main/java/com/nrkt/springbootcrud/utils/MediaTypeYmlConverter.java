package com.nrkt.springbootcrud.utils;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public final class MediaTypeYmlConverter extends AbstractJackson2HttpMessageConverter {
    public MediaTypeYmlConverter() {
        super(new YAMLMapper(), MediaType.parseMediaType("application/x-yaml"));
    }
}
