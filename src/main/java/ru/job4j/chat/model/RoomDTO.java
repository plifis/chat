package ru.job4j.chat.model;

import ru.job4j.chat.controller.Operations;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RoomDTO {
    @NotNull(groups = {Operations.OnUpdate.class, Operations.OnPatch.class})
    private int id;
    @NotBlank(message = "Login must not be empty.")
    private String theme;
    private Set<Message> messages = new HashSet<>();

    public RoomDTO() {
    }

    public RoomDTO(String theme) {
        this.theme = theme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public boolean addMessage(Message message) {
        return this.messages.add(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoomDTO roomDTO = (RoomDTO) o;
        return id == roomDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
