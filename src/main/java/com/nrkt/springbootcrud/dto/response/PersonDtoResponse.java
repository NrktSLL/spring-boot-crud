package com.nrkt.springbootcrud.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@ApiModel(value = "Person Response")
public class PersonDtoResponse extends RepresentationModel<PersonDtoResponse> {

    @Getter(AccessLevel.NONE)
    @ApiModelProperty(hidden = true)
    private final List<Link> links;

    @ApiModelProperty(notes = "Person Id",required = true)
    Long id;

    @ApiModelProperty(notes = "Person Name", example = "Nrkt", required = true, position = 1)
    String name;

    @ApiModelProperty(notes = "Person Surname", example = "Sll", required = true, position = 2)
    String surname;

    @ApiModelProperty(notes = "Person Born Date", required = true, position = 3)
    Date born;

    @ApiModelProperty(notes = "Person Phone", position = 5, example = "5584312588")
    String phone;

    @ApiModelProperty(notes = "Person Email", example = "aa@gmail.com", position = 4)
    @JsonProperty(value = "email")
    String mail;
}
