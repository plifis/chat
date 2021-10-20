package ru.job4j.chat.model;

import ru.job4j.chat.controller.Operations;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

public class RoleDTO {
    @NotNull(groups = {Operations.OnUpdate.class, Operations.OnDelete.class, Operations.OnPatch.class})
    private int id;
    @NotBlank(message = "Login must not be empty.")
    private String role;
    private Set<Person> list = new HashSet<>();

    public RoleDTO() {
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

    public Set<Person> getSet() {
        return list;
    }

    public void setSet(Set<Person> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleDTO roleDTO = (RoleDTO) o;
        return id == roleDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
