package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Person;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    @EntityGraph(attributePaths = {"role"})
    public Person findPersonByLogin(String login);

    @EntityGraph(attributePaths = {"role"})
    public Iterable<Person> findAll();

    @EntityGraph(attributePaths = {"role"})
    public Optional<Person> findById(int id);
}
