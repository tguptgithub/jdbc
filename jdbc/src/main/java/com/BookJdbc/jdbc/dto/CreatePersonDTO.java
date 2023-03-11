package com.BookJdbc.jdbc.dto;

import com.BookJdbc.jdbc.model.Person;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePersonDTO {
    @NotBlank(message="First name not be blank")
    private String firstname;
    private String lastName;
    @NotBlank(message="Dob not be blank")
    private String dob;

    public Person to(){
        return Person.builder()
                .firstname(firstname)
                .lastName(lastName)
                .dob(dob)
                .build();

    }
}
