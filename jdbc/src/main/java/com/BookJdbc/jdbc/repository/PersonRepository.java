package com.BookJdbc.jdbc.repository;


import com.BookJdbc.jdbc.model.Person;
import java.util.List;

public interface PersonRepository  {
    void createPersonStatic(Person person);
    void createPerson(Person person);
    Person getPersonById(int id);
    boolean deletePersonById(int id);
    List<Person> getAllPersons();
}
