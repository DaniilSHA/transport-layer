package ru.example.store;

import org.springframework.stereotype.Repository;
import ru.example.exceptions.NoValidIdException;
import ru.example.exceptions.PersonNotFoundException;
import ru.example.model.Gender;
import ru.example.model.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MemoryStore implements Store {

    private final List<Person> people = new ArrayList<>();


    @Override
    public void create(Person person) {

        if (person.getId() != 0)
            throw new NoValidIdException();

        int currentMaxId = people.stream()
                .max(Comparator.comparing(Person::getId))
                .map(Person::getId)
                .orElse(0);

        int newId = currentMaxId + 1;
        person.setId(newId);

        people.add(person);
    }

    @Override
    public void update(Person person) {
        Person personFromList = people.stream()
                .filter(p -> p.getId() == person.getId())
                .findFirst()
                .orElseThrow(PersonNotFoundException::new);

        personFromList.setName(person.getName());
        personFromList.setAge(person.getAge());
        personFromList.setGender(person.getGender());
        personFromList.setTelNumber(person.getTelNumber());
    }

    @Override
    public void delete(int id) {
        Person personFromList = people.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(PersonNotFoundException::new);

        people.remove(personFromList);
    }

    @Override
    public Person read(int id) {
        return people.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(PersonNotFoundException::new);
    }

    @Override
    public List<Person> readAll(Gender gender) {
        if (gender != null)
            return people.stream()
                    .filter(p -> p.getGender() == gender)
                    .collect(Collectors.toList());
        else
            return people;
    }
}
