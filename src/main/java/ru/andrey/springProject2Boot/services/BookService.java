package ru.andrey.springProject2Boot.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrey.springProject2Boot.models.Book;
import ru.andrey.springProject2Boot.models.Human;
import ru.andrey.springProject2Boot.repositories.BookRepository;
import ru.andrey.springProject2Boot.repositories.HumanRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    BookRepository bookRepository;
    HumanRepository humanRepository;

    public BookService(BookRepository bookRepository, HumanRepository humanRepository) {
        this.bookRepository = bookRepository;
        this.humanRepository = humanRepository;
    }

    public List<Book> getBooks(Boolean sort_by_year) {
        System.out.println(sort_by_year);
        if (sort_by_year) return bookRepository.findAll(Sort.by("yearOfRelease"));
        else return bookRepository.findAll();
    }

    public List<Book> getBooks(Integer page, Integer books_per_page, Boolean sort_by_year) {
        if (page != null && books_per_page != null)
            if (sort_by_year)
                return bookRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("yearOfRelease"))).getContent();
            else return bookRepository.findAll(PageRequest.of(page, books_per_page)).getContent();
        else return getBooks(sort_by_year);
    }

    public Book getBook(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addBook(Book book) {
        book.setData_of_taken(new Date());
        bookRepository.save(book);
    }

    @Transactional
    public void updateBook(Book book, int id) {
        Book bookToBeUpdated = bookRepository.findById(id).get();

        book.setOwner(bookToBeUpdated.getOwner());
        book.setId(id);

        bookRepository.save(book);
    }

    public Optional<Book> getBook(String name) {
        return bookRepository.findByName(name);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void setHuman(int id_book, int id_human) {
        Book book = bookRepository.findById(id_book).orElse(null);
        Human human = humanRepository.findById(id_human).orElse(null);

        book.setOwner(human);
        book.setData_of_taken(new Date());
    }

    public Optional<Human> getHuman(int id) {
        return humanRepository.findById(id);
    }

    @Transactional
    public void unsetHuman(int id) {
        Book book = bookRepository.findById(id).orElse(null);
        book.setOwner(null);
        book.setData_of_taken(null);
    }

    public List<Book> findDistinctByNameStartingWith(String searchQuery) {
        return bookRepository.findDistinctByNameStartingWith(searchQuery);
    }
}
