package com.nrkt.springbootcrud.mapper;

import com.nrkt.springbootcrud.dto.request.PersonDtoRequest;
import com.nrkt.springbootcrud.model.Person;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

public class PersonMap extends PropertyMap<PersonDtoRequest, Person> {

    Converter<String, String> toUppercase = ctx -> ctx.getSource() == null
            ? null
            : ctx.getSource().toUpperCase();

    @Override
    protected void configure() {
        using(toUppercase).map(source.getSurname(), destination.getLastName());
        skip(destination.getId());
    }
}
