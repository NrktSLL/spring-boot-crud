package com.nrkt.springbootcrud.service;

import com.nrkt.springbootcrud.dto.PersonDto;
import com.nrkt.springbootcrud.model.Person;

import java.util.List;

public interface PersonService {
    PersonDto addPerson(PersonDto person);

    List<PersonDto> addAllPerson(List<PersonDto> person);

    PersonDto updatePerson(long id, PersonDto person);

    void removePerson(long id);

    PersonDto getPerson(Long id);

    List<PersonDto> getAllPerson();

    List<PersonDto> bringPersonByName(String name);

    PersonDto bringPersonByMail(String name);
}
