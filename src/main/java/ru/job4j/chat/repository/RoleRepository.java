package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNullApi;
import ru.job4j.chat.model.Role;

import java.util.Collection;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    @EntityGraph(attributePaths = "person")
    public Iterable<Role> findAll();

    @EntityGraph(attributePaths = "person")
    public Optional<Role> findById(int id);
}
