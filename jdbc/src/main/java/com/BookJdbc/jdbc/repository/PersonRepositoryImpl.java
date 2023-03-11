
package com.BookJdbc.jdbc.repository;

import com.BookJdbc.jdbc.dto.CreatePersonDTO;
import com.BookJdbc.jdbc.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepositoryImpl implements PersonRepository{
    private static final Logger log = LoggerFactory.getLogger(PersonRepositoryImpl.class);
    private Connection connection;
    private PreparedStatement preparedStatement;
    @Autowired  // when we have multiple constructor with diff parameter then we can use autowired.
    public PersonRepositoryImpl(Connection connection) {
        this.connection = connection;
        creatTable();
    }
    private void creatTable()  {
        try {
            Statement statement = connection.createStatement();
            statement.execute("create table if not exists person(id int primary key auto_increment," +
                    "firstname varchar(30)," +
                    "lastname varchar(30)," +
                    "age int," +
                    "dob varchar(12))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void createPersonStatic(Person person) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
           int result= statement.executeUpdate("insert into person(firstName, lastName, age, dob) values('Tanu','gupta',21,'22/7/2002')");
        log.info("insert result {}", result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void createPerson(Person person) {
        try {
            this.preparedStatement = connection.prepareStatement("insert into person(firstName,lastName, age, dob) values(?,?,?,?)");
            preparedStatement.setString(1, person.getFirstname());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setInt(3, person.getAge());
            preparedStatement.setString(4, person.getDob());
            int result = preparedStatement.executeUpdate();
            log.info("Insert result {}", result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Person getPersonById(int id) {
        try {
            this.preparedStatement = connection.prepareStatement("Select * from person where id= ?");
            preparedStatement.setInt(1, id);
            ResultSet result =preparedStatement.executeQuery();
            log.info("result", result);
            while(result.next()){
                return getPersonFromRresultSet(result);

            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return null;
    }

    private Person getPersonFromRresultSet(ResultSet resultSet){
        try {
           return Person.builder().id(resultSet.getInt("id"))
                   .firstname(resultSet.getString("firstname"))
                           .lastName(resultSet.getString("lastname"))
                   .dob(resultSet.getString(5)).age(resultSet.getInt(4)).build();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deletePersonById(int id) {
        try {
            this.preparedStatement = connection.prepareStatement("delete from person where id = ?");
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            return (result>0)? true:false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Person> getAllPersons() {
        List<Person> personList=new ArrayList<Person>();
        try {
            Statement statement=connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from person");
            while(resultSet.next()){
                Person person=getPersonFromRresultSet(resultSet);
                personList.add(person);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return personList;
    }
}
