package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.model.RoomDTO;
import ru.job4j.chat.service.RoomService;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService service;

    public RoomController(RoomService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Room> getAllRooms() {
        return this.service.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable int id) {
        var rsl = this.service.findById(id);
        if (rsl.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("RoomHeader", "Room " + rsl.get().getTheme())
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(rsl.get().toString().length())
                .body(rsl.get());
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@Valid @RequestBody Room room) {
        if (room == null) {
            throw new NullPointerException("Room can not empty");
        }
        return new ResponseEntity<>(
                this.service.saveRoom(room), HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    @Validated({Operations.OnUpdate.class})
    public ResponseEntity<Void> update(@RequestBody Room room) {
        if (room == null) {
            throw new NullPointerException("Room can not empty");
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    @Validated({Operations.OnPatch.class})
    public ResponseEntity<Void> patch(@RequestBody RoomDTO dto) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (dto == null) {
            throw new NullPointerException("Room can not empty");
        }
        this.service.patch(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        Room room = new Room();
        room.setId(id);
        this.service.deleteRoom(room);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Room delete with id = " + id);
    }



}
