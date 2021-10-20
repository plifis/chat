package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.MessageDTO;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.MessageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/message")
public class MessageController {
    RestTemplate rest = new RestTemplate();
    private MessageService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class.getSimpleName());
    private final ObjectMapper mapper;

    public MessageController(MessageService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/")
    public ResponseEntity<Message> create(@Valid @RequestBody Message message) {
        if (message == null) {
            throw new NullPointerException("Message can not empty");
        }
        if (message.getText().length() < 1) {
            throw new IllegalArgumentException("Text must be contain 1 or more symbols");
        }
        return new ResponseEntity<>(
                this.service.saveMessage(message), HttpStatus.OK
        );
    }

    @PutMapping("/")
    @Validated(Operations.OnUpdate.class)
    public ResponseEntity<Void> update(@RequestBody Message message) {
        if (message == null) {
            throw new NullPointerException("Message can not empty");
        }
        if (message.getText().length() < 1) {
            throw new IllegalArgumentException("Text must be contain 1 or more symbols");
        }
        this.service.saveMessage(message);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    @Validated(Operations.OnPatch.class)
    public ResponseEntity<Void> patch(@RequestBody MessageDTO dto) {
        if (dto == null) {
            throw new NullPointerException("Message can not empty");
        }
        var message = this.service.getById(dto.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        message.setText(dto.getText());
        message.setRoom(rest.getForObject("/room/{id}", Room.class, dto.getIdRoom()));
        this.service.saveMessage(message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Message message = new Message();
        message.setId(id);
        this.service.deleteMessage(message);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpStatus.BAD_REQUEST.value());
        resp.setContentType("application/json");
        resp.getWriter().write(mapper.writeValueAsString(new HashMap<>() {{
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }
}
