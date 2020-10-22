package com.nrkt.springbootcrud.service.impl;

import com.nrkt.springbootcrud.dto.PersonDto;
import com.nrkt.springbootcrud.exception.PersonNotFoundException;
import com.nrkt.springbootcrud.model.Person;
import com.nrkt.springbootcrud.repository.PersonRepository;
import com.nrkt.springbootcrud.service.PersonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
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
    public PersonDto addPerson(PersonDto newPerson) {
        Person person = customModelMapper.map(newPerson, Person.class);
        personRepository.save(person);

        return customModelMapper.map(person, PersonDto.class);
    }

    @Override
    public List<PersonDto> addAllPerson(List<PersonDto> personDtoList) {

        List<Person> personList = personDtoList
                .stream()
                .map(dto -> customModelMapper.map(dto, Person.class))
                .collect(Collectors.toList());

        personList = personRepository.saveAll(personList);

        return Arrays.asList(customModelMapper.map(personList, PersonDto[].class));
    }

    @Override
    public PersonDto updatePerson(long id, PersonDto personDto) {
        var existPerson = personRepository
                .findById(id)
                .orElseThrow(PersonNotFoundException::new);

        existPerson.setName(personDto.getName());
        existPerson.setLastName(personDto.getSurname());
        existPerson.setMail(personDto.getMail());
        existPerson.setBorn(personDto.getBorn());
        existPerson.setPhone(personDto.getPhone());

        Person person = personRepository.save(existPerson);

        return customModelMapper.map(person, PersonDto.class);
    }

    @Override
    public void removePerson(long id) {
        personRepository.deleteById(id);
    }

    @Override
    public PersonDto getPerson(Long id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        return customModelMapper.map(person, PersonDto.class);
    }

    @Override
    public List<PersonDto> getAllPerson() {
        return personRepository
                .findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(person -> customModelMapper.map(person, PersonDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonDto> bringPersonByName(String name) {
        return personRepository.findByName(name)
                .stream()
                .map(person -> customModelMapper.map(person, PersonDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PersonDto bringPersonByMail(String mail) {
        return personRepository.findByMail(mail)
                .stream()
                .map(person -> customModelMapper.map(person, PersonDto.class))
                .findFirst().orElseThrow(PersonNotFoundException::new);
    }
}
