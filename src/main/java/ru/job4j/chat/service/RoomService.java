package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.RoleDTO;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.model.RoomDTO;
import ru.job4j.chat.repository.RoomRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository rep;

    public RoomService(RoomRepository rep) {
        this.rep = rep;
    }

    public List<Room> getAllRooms() {
        List<Room> rsl = new ArrayList<>();
        rep.findAll().forEach(rsl::add);
        return rsl;
    }

    public Room saveRoom(Room room) {
        return this.rep.save(room);
    }

    public Optional<Room> findById(int id) {
        return this.rep.findById(id);
    }

    public Room patch(RoomDTO dto) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var room = this.rep.findById(dto.getId());
        if (room.isPresent()) {
            for (Method methodDTO : dto.getClass().getDeclaredMethods()) {
                if (methodDTO.getName().startsWith("get")) {
                    var newValue = methodDTO.invoke(dto);
                    var setMethodName = methodDTO.getName().replace("get", "set");
                    var setMethod = room.getClass().getDeclaredMethod(setMethodName);
                    setMethod.invoke(room.get(), newValue);
                }
            }
        }
        return room.get();
    }

    public void deleteRoom(Room room) {
        this.rep.delete(room);
    }
}
