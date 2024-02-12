package ru.andrey.springProject2Boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andrey.springProject2Boot.models.Human;

import java.util.Optional;

@Repository
public interface HumanRepository extends JpaRepository<Human, Integer> {
    public Optional<Human> findByFio(String fio);
}
