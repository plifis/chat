package ru.job4j.chat.repository;

import com.sun.source.tree.IdentifierTree;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Room;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    @EntityGraph(attributePaths = "messages")
    public Iterable<Room> findAll();

    @EntityGraph(attributePaths = "messages")
    public Optional<Room> findById(int id);
}
