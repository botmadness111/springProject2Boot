package ru.andrey.springProject2Boot.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.andrey.springProject2Boot.models.Book;
import ru.andrey.springProject2Boot.services.BookService;

@Component
public class BookValidator implements Validator {

    BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (bookService.getBook(book.getName()).isPresent()) {
            errors.rejectValue("name", "", "name should be Unique!");
        }
    }
}
