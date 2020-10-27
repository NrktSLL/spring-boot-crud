package com.nrkt.springbootcrud.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;


import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@ApiModel(value = "Person Request", description = "Person's entity")
public class PersonDtoRequest {

    @NotBlank
    @ApiModelProperty(notes = "Person Name", example = "Nrkt", required = true, position = 1)
    String name;

    @NotBlank
    @ApiModelProperty(notes = "Person Surname", example = "Sll", required = true, position = 2)
    String surname;

    @PastOrPresent
    @NotNull
    @ApiModelProperty(notes = "Person Born Date", required = true, position = 3)
    Date born;

    @Pattern(regexp = "[0-9\\s]{10}")
    @ApiModelProperty(notes = "Person Phone", position = 5, example = "5584312588")
    String phone;

    @Email
    @ApiModelProperty(notes = "Person Email", example = "aa@gmail.com", position = 4)
    @JsonProperty(value = "email")
    String mail;
}
