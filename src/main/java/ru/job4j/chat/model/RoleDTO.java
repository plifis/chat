package ru.job4j.chat.model;

import java.util.*;

public class RoleDTO {
    private int id;
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
