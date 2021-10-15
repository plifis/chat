package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.RoomRepository;

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

    public void deleteRoom(Room room) {
        this.rep.delete(room);
    }
}
