package ru.andrey.springProject2Boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andrey.springProject2Boot.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    public Optional<Book> findByName(String name);

    public List<Book> findDistinctByNameStartingWith(String searchQuery);
}
