package com.nrkt.springbootcrud.service.impl;

import com.nrkt.springbootcrud.exception.PersonNotFoundException;
import com.nrkt.springbootcrud.model.Person;
import com.nrkt.springbootcrud.repository.PersonRepository;
import com.nrkt.springbootcrud.service.PersonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    PersonRepository personRepository;

    @Override
    public Person addPerson(Person newPerson) {
        return personRepository.save(newPerson);
    }

    @Override
    public List<Person> addAllPerson(List<Person> person) {
        return personRepository.saveAll(person);
    }

    @Override
    public Person updatePerson(long id, Person person) {
        var existPerson = personRepository
                .findById(id)
                .orElseThrow(PersonNotFoundException::new);

        existPerson.setName(person.getName());
        existPerson.setLastName(person.getLastName());
        existPerson.setMail(person.getMail());
        existPerson.setBorn(person.getBorn());
        existPerson.setPhone(person.getPhone());

        personRepository.save(existPerson);

        return existPerson;
    }

    @Override
    public void removePerson(long id) {
        personRepository.deleteById(id);
    }

    @Override
    public Person getPerson(Long id) {
        return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    @Override
    public List<Person> getAllPerson() {
        return personRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public List<Person> bringPersonByName(String name) {
        return personRepository.findByName(name);
    }

    @Override
    public Person bringPersonByMail(String email) {
        return personRepository.findByMail(email).orElseThrow(PersonNotFoundException::new);
    }
}
