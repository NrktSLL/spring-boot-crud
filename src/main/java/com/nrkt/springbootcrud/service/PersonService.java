package com.nrkt.springbootcrud.service;

import com.nrkt.springbootcrud.dto.request.PersonDtoRequest;
import com.nrkt.springbootcrud.dto.response.PersonDtoResponse;

import java.util.List;

public interface PersonService {
    PersonDtoResponse addPerson(PersonDtoRequest person);

    List<PersonDtoResponse> addAllPerson(List<PersonDtoRequest> person);

    PersonDtoResponse updatePerson(long id, PersonDtoRequest person);

    void removePerson(long id);

    PersonDtoResponse getPerson(Long id);

    List<PersonDtoResponse> getAllPerson();

    List<PersonDtoResponse> bringPersonByName(String name);

    PersonDtoResponse bringPersonByMail(String name);
}
