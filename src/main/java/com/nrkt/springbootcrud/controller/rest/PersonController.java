package com.nrkt.springbootcrud.controller.rest;

import com.nrkt.springbootcrud.dto.PersonDto;
import com.nrkt.springbootcrud.model.Person;
import com.nrkt.springbootcrud.service.PersonService;
import io.swagger.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "v1/persons")
@Api(tags = "persons")
public class PersonController {

    PersonService personService;
    ModelMapper customModelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("New Person")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Person Created"),
            @ApiResponse(code = 404, message = "Person Not Found !"),
            @ApiResponse(code = 400, message = "Validate Error !"),
            @ApiResponse(code = 500, message = "Internal Server Error !")
    })
    public PersonDto createPerson(
            @ApiParam(value = "Person Specifications", required = true)
            @RequestBody @Valid PersonDto personDto) {

        var person = new Person();
        customModelMapper.map(personDto, person);

        person = personService.addPerson(person);

        var links = new Link[]{
                linkTo(methodOn(PersonController.class).getPerson(person.getId()))
                        .withRel("person")
                        .withType("GET")
                        .withDeprecation("Get Person"),
                linkTo(methodOn(PersonController.class).getPersonList())
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get All Person"),
                linkTo(methodOn(PersonController.class).editPerson(person.getId(), personDto))
                        .withSelfRel()
                        .withDeprecation("Edit Person")
                        .withType("POST")
        };

        personDto = customModelMapper.map(person, PersonDto.class);

        return personDto.add(links);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Update Person")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Person not found!"),
            @ApiResponse(code = 400, message = "Validate Error"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public PersonDto editPerson(
            @ApiParam(value = "Existing person ID", required = true)
            @PathVariable long id, @ApiParam(value = "Person Specifications", required = true)
            @RequestBody @Valid PersonDto personDto) {

        var person = new Person();
        customModelMapper.map(personDto, person);

        person = personService.updatePerson(id, person);

        var links = new Link[]{
                linkTo(methodOn(PersonController.class).getPerson(person.getId()))
                        .withRel("person")
                        .withType("GET")
                        .withDeprecation("Get Person"),
                linkTo(methodOn(PersonController.class).getPersonList())
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get All Person")
        };

        personDto = customModelMapper.map(person, PersonDto.class);

        return personDto.add(links);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete Person", notes = "No HATEOS info")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Person not found!"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public void deletePerson(@PathVariable long id) {
        personService.removePerson(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get Person")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Person not found!"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public PersonDto getPerson(
            @ApiParam(value = "Existing person ID", required = true)
            @PathVariable long id) {
        Person person = personService.getPerson(id);

        var links = new Link[]{
                linkTo(methodOn(PersonController.class).createPerson(new PersonDto()))
                        .withSelfRel()
                        .withType("POST")
                        .withDeprecation("Add Person"),
                linkTo(methodOn(PersonController.class).getPersonList())
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get All Person")
        };

        var personDto = new PersonDto();
        personDto = customModelMapper.map(person, PersonDto.class);

        return personDto.add(links);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get All Person")
    @ApiResponse(code = 500, message = "Internal Server Error")
    public List<PersonDto> getPersonList() {
        var personList = personService.getAllPerson();

        List<PersonDto> personDtoList = personList
                .stream()
                .map(person -> customModelMapper.map(person, PersonDto.class))
                .collect(Collectors.toList());

        Link link = linkTo(
                methodOn(PersonController.class).createPerson(new PersonDto()))
                .withSelfRel()
                .withType("POST")
                .withDeprecation("Add Person");

        personDtoList.forEach(person -> person.add(link));

        return personDtoList;
    }

    @GetMapping("/bymail")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Bring Person Information by Mail")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Person not found!"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public PersonDto getPersonByMail(@RequestHeader(value = "email") String mail) {
        Person person = personService.bringPersonByMail(mail);

        var links = new Link[]{
                linkTo(methodOn(PersonController.class).createPerson(new PersonDto()))
                        .withSelfRel()
                        .withType("POST")
                        .withDeprecation("Add Person"),
                linkTo(methodOn(PersonController.class).getPersonList())
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get All Person")
        };

        var personDto = new PersonDto();
        personDto = customModelMapper.map(person, PersonDto.class);

        return personDto.add(links);
    }

    @GetMapping("/byname")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Bring Person Information List by Name")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<PersonDto> getPersonByName(@RequestHeader(value = "name") String name) {
        List<Person> personList = personService.bringPersonByName(name);

        var links = new Link[]{
                linkTo(methodOn(PersonController.class).createPerson(new PersonDto()))
                        .withSelfRel()
                        .withType("POST")
                        .withDeprecation("Add Person"),
                linkTo(methodOn(PersonController.class).getPersonList())
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get All Person")
        };

        List<PersonDto> personDtoList = personList
                .stream()
                .map(person -> customModelMapper.map(person, PersonDto.class))
                .collect(Collectors.toList());

        personDtoList.forEach(person -> person.add(links));

        return personDtoList;
    }
}
