package com.nrkt.springbootcrud.controller.rest;

import com.nrkt.springbootcrud.dto.PersonDto;
import com.nrkt.springbootcrud.service.PersonService;
import io.swagger.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "v1/persons")
@Api(tags = "persons")
public class PersonController {

    PersonService personService;

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
            @RequestBody @Valid PersonDto person) {

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
                linkTo(methodOn(PersonController.class).editPerson(person.getId(), person))
                        .withSelfRel()
                        .withDeprecation("Edit Person")
                        .withType("POST"),
                linkTo(methodOn(PersonController.class).getPersonByMail(person.getMail()))
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get Person by email"),
                linkTo(methodOn(PersonController.class).getPersonByName(person.getName()))
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get Person by email"),
        };

        return person.add(links);
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
            @RequestBody @Valid PersonDto person) {

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
                        .withDeprecation("Get All Person"),
                linkTo(methodOn(PersonController.class).getPersonByMail(person.getMail()))
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get Person by email"),
                linkTo(methodOn(PersonController.class).getPersonByName(person.getName()))
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get Person by email")
        };

        return person.add(links);
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

        PersonDto person = personService.getPerson(id);

        var links = new Link[]{
                linkTo(methodOn(PersonController.class).createPerson(new PersonDto()))
                        .withSelfRel()
                        .withType("POST")
                        .withDeprecation("Add Person"),
                linkTo(methodOn(PersonController.class).getPersonList())
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get All Person"),
                linkTo(methodOn(PersonController.class).getPersonByMail(person.getMail()))
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get Person by email"),
                linkTo(methodOn(PersonController.class).getPersonByName(person.getName()))
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get Person by email")
        };

        return person.add(links);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get All Person")
    @ApiResponse(code = 500, message = "Internal Server Error")
    public List<PersonDto> getPersonList() {

        var personList = personService.getAllPerson();

        Link link = linkTo(
                methodOn(PersonController.class).createPerson(new PersonDto()))
                .withSelfRel()
                .withType("POST")
                .withDeprecation("Add Person");

        personList.forEach(person -> person.add(link));

        return personList;
    }

    @GetMapping("/findbyemail")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Bring Person Information by Mail")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Person not found!"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public PersonDto getPersonByMail(@RequestHeader(value = "email") String mail) {

        PersonDto person = personService.bringPersonByMail(mail);

        var links = new Link[]{
                linkTo(methodOn(PersonController.class).createPerson(new PersonDto()))
                        .withSelfRel()
                        .withType("POST")
                        .withDeprecation("Add Person"),
                linkTo(methodOn(PersonController.class).getPersonList())
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get All Person"),
                linkTo(methodOn(PersonController.class).getPersonByName(person.getName()))
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get Person by email")
        };

        return person.add(links);
    }

    @GetMapping("/findbyname")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Bring Person Information List by Name")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<PersonDto> getPersonByName(@RequestHeader(value = "name") String name) {
        List<PersonDto> personList = personService.bringPersonByName(name);

        var links = new Link[]{
                linkTo(methodOn(PersonController.class).createPerson(new PersonDto()))
                        .withSelfRel()
                        .withType("POST")
                        .withDeprecation("Add Person"),
                linkTo(methodOn(PersonController.class).getPersonList())
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get All Person"),
                linkTo(methodOn(PersonController.class).getPersonByMail(new PersonDto().getMail()))
                        .withRel("person")
                        .withType("GET")
                        .withTitle("Persons")
                        .withDeprecation("Get Person by email"),
        };

        personList.forEach(person -> person.add(links));

        return personList;
    }
}
