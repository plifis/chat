package ru.job4j.chat.model;

import ru.job4j.chat.controller.Operations;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = {Operations.OnUpdate.class, Operations.OnPatch.class})
    private int id;
    @NotBlank(message = "Role must not be empty.")
    private String role;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    private Set<Person> person = new HashSet<>();

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Person> getPerson() {
        return person;
    }

    public void setPerson(Set<Person> person) {
        this.person = person;
    }

    public boolean addPerson(Person person) {
        return this.person.add(person);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return id == role.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
