package ru.example.store;

import ru.example.model.Gender;
import ru.example.model.Person;

import java.util.List;

public interface Store {

    void create(Person person);
    void update(Person person);

    void delete(int id);

    Person read(int id);

    List<Person> readAll(Gender gender);

}
