package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.model.RoleDTO;
import ru.job4j.chat.service.RoleService;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Role> getAllRoles() {
        return this.service.getAllRoles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable int id) {
        var rsl = this.service.getById(id);
        if (rsl.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }
        return new ResponseEntity<>(rsl.get(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Role> create(@Valid @RequestBody Role role) {
        if (role == null) {
            throw new NullPointerException("Person can not empty");
        }
        return new ResponseEntity<>(
                this.service.saveRole(role), HttpStatus.CREATED);
    }

    @PutMapping("/")
    @Validated({Operations.OnUpdate.class})
    public ResponseEntity<Void> update(@RequestBody Role role) {
        if (role == null) {
            throw new NullPointerException("Person can not empty");
        }
        this.service.saveRole(role);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    @Validated({Operations.OnPatch.class})
    public ResponseEntity<Void> patch(@RequestBody RoleDTO dto) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (dto == null) {
            throw new NullPointerException("Person can not empty");
        }
        this.service.patchRole(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Role role = new Role();
        role.setId(id);
        this.service.deleteRole(role);
        return ResponseEntity.ok().build();
    }

}
