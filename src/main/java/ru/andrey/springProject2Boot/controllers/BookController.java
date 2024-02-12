package ru.andrey.springProject2Boot.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andrey.springProject2Boot.models.Book;
import ru.andrey.springProject2Boot.models.Human;
import ru.andrey.springProject2Boot.services.BookService;
import ru.andrey.springProject2Boot.services.HumanService;
import ru.andrey.springProject2Boot.validators.BookValidator;

@Controller
@RequestMapping("/book")
public class BookController {

    BookService bookService;
    HumanService humanService;
    BookValidator bookValidator;

    @Autowired
    public BookController(BookService bookService, BookValidator bookValidator, HumanService humanService) {
        this.bookService = bookService;
        this.bookValidator = bookValidator;
        this.humanService = humanService;
    }

    @GetMapping("/all")
    public String getBooksPage(Model model,
                               @RequestParam(name = "page", required = false) Integer page,
                               @RequestParam(name = "books_per_page", required = false) Integer books_per_page,
                               @RequestParam(name = "sort_by_year", required = false, defaultValue = "false") Boolean sort_by_year
    ) {
        model.addAttribute("books", bookService.getBooks(page, books_per_page, sort_by_year));

        return "/book/books";
    }

    @GetMapping("/add")
    public String getAddBookPage(Model model) {
        model.addAttribute("book", new Book());

        return "/book/addBook";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/book/addBook";
        }

        bookService.addBook(book);

        return "redirect:/book/all";
    }

    @GetMapping("/edit/{id}")
    public String getUpdatePage(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.getBook(id));

        return "/book/editBook";
    }

    @PatchMapping("/edit/{id}")
    public String updateBook(@ModelAttribute @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/book/editBook";
        }

        bookService.updateBook(book, id);

        return "redirect:/book/all";
    }

    @GetMapping("/{id}")
    public String getBookPage(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.getBook(id));
        model.addAttribute("people", humanService.getPeople());

        Human owner = bookService.getBook(id).getOwner();
        if (owner == null) model.addAttribute("human", new Human());
        else
            model.addAttribute("human", bookService.getHuman(bookService.getBook(id).getOwner().getId()).orElse(new Human()));

        return "/book/book";
    }

    @PatchMapping("/setHuman/{id}")
    public String setHuman(@ModelAttribute Human human, @PathVariable("id") int id) {
        bookService.setHuman(id, human.getId());

        return "redirect:/book/all";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@ModelAttribute Book book, @PathVariable("id") int id) {
        bookService.delete(id);

        return "redirect:/book/all";
    }

    @PatchMapping("/release/{id}")
    public String release(@PathVariable("id") int id) {
        bookService.unsetHuman(id);

        return "redirect:/book/all";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(value = "searchQuery", required = false) String searchQuery) {

        if (searchQuery != null) {
            model.addAttribute("searchedBooks", bookService.findDistinctByNameStartingWith(searchQuery));
        }
        return "/book/searchBook";

    }

}
