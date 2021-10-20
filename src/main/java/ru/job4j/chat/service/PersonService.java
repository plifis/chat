package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.PersonDTO;
import ru.job4j.chat.repository.PersonRepository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository rep;
    
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

    public Person patchPerson(PersonDTO dto) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var person = this.rep.findById(dto.getId());
        if (person.isPresent()) {
         for (Method methodDTO : dto.getClass().getDeclaredMethods()) {
             if (methodDTO.getName().startsWith("get")) {
                 var newValue = methodDTO.invoke(dto);
                 var setMethodName = methodDTO.getName().replace("get", "set");
                 var setMethod = person.getClass().getDeclaredMethod(setMethodName);
                 setMethod.invoke(person.get(), newValue);
             }
         }
         }
        return person.get();
    }
}
