package com.doza.dao;

import com.doza.dto.PersonFilter;
import com.doza.entity.Person;
import com.doza.exception.DaoException;
import com.doza.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersonDao {

    private static final PersonDao INSTANCE = new PersonDao();
    private static final String SAVE_PERSON_SQL = """
            INSERT INTO person (first_name, last_name, email, password, date_of_birth) 
            VALUES (?, ?, ?, ?, ?)  
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM person 
                   WHERE id = ?
            """;

    private static final String UPDATE_BY_ID_SQL = """
            UPDATE person 
            SET first_name = ?,
             last_name = ?,
             email = ?,
             password = ?,
             phone = ?,
             date_of_birth = ?
            WHERE id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, first_name, last_name, email, password, phone, date_of_birth
            FROM person
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;


    private PersonDao() {
    }

    public List<Person> findAll(PersonFilter personFilter) {
        List<Object> param = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if(personFilter.firstName() != null) {
            whereSql.add("first_name = ?");
            param.add(personFilter.firstName());
        }
        if(personFilter.lastName() != null) {
            whereSql.add("last_name LIKE ?");
            param.add("%" + personFilter.lastName() + "%");
        }
        param.add(personFilter.limit());
        param.add(personFilter.offset());
        String where = whereSql.stream()
                .collect(Collectors.joining(" AND ", " WHERE ", " LIMIT ? OFFSET ? "));
        var sql = FIND_ALL_SQL + where;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < param.size(); i++) {
                preparedStatement.setObject(i + 1, param.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Person> personList = new ArrayList<>();
            while (resultSet.next()) {
                personList.add(buildPerson(resultSet));
            }
            return personList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> findAll() {
        try (Connection connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL);) {
            ResultSet resultSet = prepareStatement.executeQuery();
            List<Person> persons = new ArrayList<>();
            while (resultSet.next()) {
                persons.add(buildPerson(resultSet));
            }
            return persons;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Person> findById(Long id) throws DaoException {
        try (Connection connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setLong(1, id);
            var resultSet = prepareStatement.executeQuery();
            Person person = null;
            if (resultSet.next()) {
                person = buildPerson(resultSet);
            }
            return Optional.ofNullable(person);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Person save(Person person) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(SAVE_PERSON_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, person.getFirstName());
            prepareStatement.setString(2, person.getLastName());
            prepareStatement.setString(3, person.getEmail());
            prepareStatement.setString(4, person.getPassword());
            prepareStatement.setDate(5, Date.valueOf((LocalDate) person.getDateOfBirth()));

            prepareStatement.executeUpdate();

            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                person.setId(generatedKeys.getLong("id"));
            }
            return person;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Person update(Person person) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {
            prepareStatement.setString(1, person.getFirstName());
            prepareStatement.setString(2, person.getLastName());
            prepareStatement.setString(3, person.getEmail());
            prepareStatement.setString(4, person.getPassword());
            prepareStatement.setString(5, person.getPhone());
            prepareStatement.setDate(6, Date.valueOf((LocalDate) person.getDateOfBirth()));
            prepareStatement.setLong(7, person.getId());

            prepareStatement.executeUpdate();

            return person;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            prepareStatement.setLong(1, id);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Person buildPerson(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("phone"),
                resultSet.getDate("date_of_birth").toLocalDate()
        );
    }

    public static PersonDao getInstance() {
        return INSTANCE;
    }
}
