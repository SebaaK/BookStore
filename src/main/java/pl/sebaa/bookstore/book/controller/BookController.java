package pl.sebaa.bookstore.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sebaa.bookstore.book.domain.BookDto;
import pl.sebaa.bookstore.book.model.Book;
import pl.sebaa.bookstore.book.service.BookService;

import static pl.sebaa.bookstore.book.mapper.BookMapper.bookDtoToBook;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addBook(@RequestBody BookDto dto) {
        Book book = bookDtoToBook(dto);
        return new ResponseEntity(
                service.addNewTitle(book),
                HttpStatus.CREATED
        );
    }
}
