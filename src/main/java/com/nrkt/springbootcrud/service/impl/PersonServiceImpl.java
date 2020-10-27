package com.nrkt.springbootcrud.service.impl;

import com.nrkt.springbootcrud.dto.request.PersonDtoRequest;
import com.nrkt.springbootcrud.dto.response.PersonDtoResponse;
import com.nrkt.springbootcrud.exception.PersonNotFoundException;
import com.nrkt.springbootcrud.model.Person;
import com.nrkt.springbootcrud.repository.PersonRepository;
import com.nrkt.springbootcrud.service.PersonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    PersonRepository personRepository;
    ModelMapper customModelMapper;

    @Override
    public PersonDtoResponse addPerson(PersonDtoRequest newPerson) {
        Person person = customModelMapper.map(newPerson, Person.class);
        personRepository.save(person);

        return customModelMapper.map(person, PersonDtoResponse.class);
    }

    @Override
    public List<PersonDtoResponse> addAllPerson(List<PersonDtoRequest> personDtoResponseList) {
        List<Person> personList = personDtoResponseList
                .stream()
                .map(dto -> customModelMapper.map(dto, Person.class))
                .collect(Collectors.toList());

        personList = personRepository.saveAll(personList);

        return Arrays.asList(customModelMapper.map(personList, PersonDtoResponse[].class));
    }

    @Override
    public PersonDtoResponse updatePerson(long id, PersonDtoRequest personDtoResponse) {
        var existPerson = personRepository
                .findById(id)
                .orElseThrow(PersonNotFoundException::new);

        existPerson.setName(personDtoResponse.getName());
        existPerson.setLastName(personDtoResponse.getSurname());
        existPerson.setMail(personDtoResponse.getMail());
        existPerson.setBorn(personDtoResponse.getBorn());
        existPerson.setPhone(personDtoResponse.getPhone());

        Person person = personRepository.save(existPerson);

        return customModelMapper.map(person, PersonDtoResponse.class);
    }

    @Override
    public void removePerson(long id) {
        personRepository.deleteById(id);
    }

    @Override
    public PersonDtoResponse getPerson(Long id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        return customModelMapper.map(person, PersonDtoResponse.class);
    }

    @Override
    public List<PersonDtoResponse> getAllPerson() {
        return personRepository.findAll()
                .stream()
                .map(person -> customModelMapper.map(person, PersonDtoResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonDtoResponse> bringPersonByName(String name) {
        return personRepository.findByName(name)
                .stream()
                .map(person -> customModelMapper.map(person, PersonDtoResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public PersonDtoResponse bringPersonByMail(String mail) {
        return personRepository.findByMail(mail)
                .stream()
                .map(person -> customModelMapper.map(person, PersonDtoResponse.class))
                .findFirst().orElseThrow(PersonNotFoundException::new);
    }
}
