package com.nrkt.springbootcrud.service;

import com.nrkt.springbootcrud.model.Person;

import java.util.List;

public interface PersonService {
    Person addPerson(Person person);

    List<Person> addAllPerson(List<Person> person);

    Person updatePerson(long id, Person person);

    void removePerson(long id);

    Person getPerson(Long id);

    List<Person> getAllPerson();

    List<Person> bringPersonByName(String name);

    Person bringPersonByMail(String name);
}
