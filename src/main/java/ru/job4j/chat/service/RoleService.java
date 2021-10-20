package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.model.RoleDTO;
import ru.job4j.chat.repository.RoleRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    public Role patchRole(RoleDTO dto) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var role = this.rep.findById(dto.getId());
        if (role.isPresent()) {
            for (Method methodDTO : dto.getClass().getDeclaredMethods()) {
                if (methodDTO.getName().startsWith("get")) {
                    var newValue = methodDTO.invoke(dto);
                    var setMethodName = methodDTO.getName().replace("get", "set");
                    var setMethod = role.getClass().getDeclaredMethod(setMethodName);
                    setMethod.invoke(role.get(), newValue);
                }
            }
        }
        return role.get();
    }
}
