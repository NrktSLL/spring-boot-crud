package com.nrkt.springbootcrud.repository;

import com.nrkt.springbootcrud.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("FROM Person p WHERE p.name = :name")
    List<Person> findByName(@Param("name") String name);

    Optional<Person> findByMail(String mail);
}
