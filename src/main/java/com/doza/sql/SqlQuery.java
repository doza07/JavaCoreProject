package com.doza.sql;

import com.doza.dao.PersonDao;
import com.doza.dto.PersonFilter;
import com.doza.entity.Person;
import com.doza.generator.GeneratorPerson;

import java.util.List;
import java.util.Optional;

public class SqlQuery {

    public static void findAllPersonWithFilter(String firstName, String lastName, int limit, int offset) {
        PersonFilter personFilter = new PersonFilter(firstName, lastName, limit, offset);
        List<Person> personList = PersonDao.getInstance().findAll(personFilter);
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    public static void findAllPerson() {
        var personDao = PersonDao.getInstance().findAll();
        for (Person person : personDao) {
            System.out.println(person.toString());
        }
    }

    public static void updatePhonePersonById(Long id, String phone) {
        PersonDao personDao = PersonDao.getInstance();
        Optional<Person> personById = findPersonById(id);
        personById.ifPresent(person -> {
            person.setPhone(phone);
            personDao.update(person);
        });
    }

    public static Optional<Person> findPersonById(Long id) {
        PersonDao personDao = PersonDao.getInstance();
        return personDao.findById(id);
    }

    public static void deletePersonById(Long id) {
        PersonDao personDao = PersonDao.getInstance();
        boolean isDelete = personDao.delete(id);
        System.out.println(isDelete);
    }

    public static void CreateOneHundredPerson() {
        GeneratorPerson generatorPerson = new GeneratorPerson();
        generatorPerson.createOneHundredPerson();
    }
}
