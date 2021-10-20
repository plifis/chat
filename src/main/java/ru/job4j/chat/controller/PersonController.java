package ru.job4j.chat.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.*;
import ru.job4j.chat.service.PersonService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService service;
    private final BCryptPasswordEncoder encoder;


    public PersonController(PersonService service, BCryptPasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    @GetMapping("/")
    public List<Person> getAllPersons() {
        return this.service.getAllPersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable int id) {
        var rsl = this.service.getById(id);
        if (rsl.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }
        return new ResponseEntity<>(rsl.get(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        if (person == null) {
            throw new NullPointerException("Person can not empty");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        return new ResponseEntity<>(
        this.service.savePerson(person), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        if (person == null) {
            throw new NullPointerException("Person can not empty");
        }
        this.service.savePerson(person);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    public ResponseEntity<Void> patch(@RequestBody PersonDTO dto) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (dto == null) {
            throw new NullPointerException("Person can not empty");
        }
            this.service.patchPerson(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        this.service.deletePerson(person);
        return ResponseEntity.ok().build();
    }
}
