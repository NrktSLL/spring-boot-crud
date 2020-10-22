package com.nrkt.springbootcrud.mapper;

import com.nrkt.springbootcrud.dto.PersonDto;
import com.nrkt.springbootcrud.model.Person;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

public class PersonDtoMap extends PropertyMap<Person, PersonDto> {

    Converter<String, String> toUppercase = ctx -> ctx.getSource() == null
            ? null
            : ctx.getSource().toUpperCase();

    @Override
    protected void configure() {
        using(toUppercase).map(source.getLastName(), destination.getSurname());
    }
}
