package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository rep;

    public RoleService(RoleRepository rep) {
        this.rep = rep;
    }

    public List<Role> getAllRoles() {
        List<Role> rsl = new ArrayList<>();
        this.rep.findAll().forEach(rsl::add);
        return rsl;
    }

    public Optional<Role> getById(int id) {
        return this.rep.findById(id);
    }

    public Role saveRole(Role role) {
        return this.rep.save(role);
    }

    public void deleteRole(Role role) {
        this.rep.delete(role);
    }
}
