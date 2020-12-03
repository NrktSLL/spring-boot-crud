package com.nrkt.springbootcrud.bootstrapper;

import com.nrkt.springbootcrud.model.Person;
import com.nrkt.springbootcrud.repository.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DataLoader implements CommandLineRunner {

    PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {
        var person = Person.builder()
                .name("Ali")
                .lastName("Sll")
                .born(new Date())
                .mail("abc@gmail.com")
                .build();

        personRepository.save(person);
    }
}
