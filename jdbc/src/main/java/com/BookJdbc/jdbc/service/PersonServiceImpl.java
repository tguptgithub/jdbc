package com.BookJdbc.jdbc.service;

import com.BookJdbc.jdbc.BadPersonRequestException;
import com.BookJdbc.jdbc.dto.CreatePersonDTO;
import com.BookJdbc.jdbc.model.Person;
import com.BookJdbc.jdbc.repository.PersonRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    PersonRepositoryImpl personRepositoryImpl;

    @Override
    public void createPersonStatic(CreatePersonDTO createPersonDTO) {
        Person person= createPersonDTO.to();
        if(person.getAge() == null){
            person.setAge(calculateAgeDOB(person.getDob()));
            personRepositoryImpl.createPerson(person);
           // personRepositoryImpl.createPersonStatic(person);
        }

    }

    private Integer calculateAgeDOB(String dob) {
        if(dob ==null){
            return null;
        }
        LocalDate dobDate = LocalDate.parse(dob);
        LocalDate currentDate = LocalDate.now();
        return Period.between(dobDate, currentDate).getYears();
    }

    @Override
    public Person getPersonById(int id) {
        return personRepositoryImpl.getPersonById(id);

    }

    @Override
    public Person deletePersonById(int id) {
        Person person = personRepositoryImpl.getPersonById(id);
        if(person == null){
            throw new BadPersonRequestException("Person with Id = "+id+"not present");
        }
        boolean isDeleted = personRepositoryImpl.deletePersonById(id);
        if(isDeleted){
            return person;
        }
        return null;
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepositoryImpl.getAllPersons();
    }
}
