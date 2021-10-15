package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.PersonRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private PersonRepository rep;
    
    public PersonService(PersonRepository rep) {
        this.rep = rep;
    }

    public List<Person> getAllPersons() {
        List<Person> rsl = new ArrayList<>();
        this.rep.findAll().forEach(rsl::add);
        return rsl;
    }

    public Optional<Person> getById(int id) {
        return this.rep.findById(id);
    }

    public Person savePerson(Person person) {
        return this.rep.save(person);
    }

    public void deletePerson(Person person) {
        this.rep.delete(person);
    }
}
