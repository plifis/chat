package ru.job4j.chat.model;


import ru.job4j.chat.controller.Operations;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class PersonDTO {
    @NotNull(groups = {Operations.OnUpdate.class, Operations.OnPatch.class})
    private int id;
    @NotBlank(message = "Login must not be empty.")
    private String login;
    @NotBlank(message = "Password must not be empty.")
    @Min(value = 6, message = "Password must be 6 or more symbols.")
    private String password;
    private Role role;

    public PersonDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonDTO personDTO = (PersonDTO) o;
        return id == personDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
