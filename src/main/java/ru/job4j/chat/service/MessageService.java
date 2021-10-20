package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.MessageDTO;
import ru.job4j.chat.repository.MessageRepository;

import java.lang.reflect.Field;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository rep;

    public MessageService(MessageRepository rep) {
        this.rep = rep;
    }

    public Optional<Message> getById(int id) {
       return this.rep.findById(id);
    }

    public Message saveMessage(Message message) {
        return rep.save(message);
    }

    public void deleteMessage(Message message) {
        this.rep.delete(message);
    }

}
