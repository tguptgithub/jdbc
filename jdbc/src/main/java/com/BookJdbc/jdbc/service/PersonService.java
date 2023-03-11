package com.BookJdbc.jdbc.service;

import com.BookJdbc.jdbc.dto.CreatePersonDTO;
import com.BookJdbc.jdbc.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
void createPersonStatic(CreatePersonDTO createPersonDTO);
Person getPersonById(int id);
Person deletePersonById(int id);
List<Person> getAllPersons();

}
