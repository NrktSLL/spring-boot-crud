package com.nrkt.springbootcrud.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public class MediaTypeYmlConverter extends AbstractJackson2HttpMessageConverter {
    public MediaTypeYmlConverter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.parseMediaType("application/x-yaml"));
    }
}
