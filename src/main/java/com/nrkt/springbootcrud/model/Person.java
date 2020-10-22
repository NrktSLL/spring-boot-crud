package com.nrkt.springbootcrud.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Persons")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotBlank
    String name;

    @NotBlank
    String lastName;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    Date born;

    @Column(columnDefinition = "boolean default true")
    Boolean status;

    @Email
    @Column(name = "Email",unique = true)
    String mail;

    @Pattern(regexp = "[0-9\\s]{10}")
    String phone;
}
