package pl.sebaa.bookstore.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sebaa.bookstore.book.model.Book;
import pl.sebaa.bookstore.book.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public Book addNewTitle(Book book) {
        return repository.save(book);
    }

    public Book getBookById(Long idBook) {
        return repository.findById(idBook).orElseThrow();
    }

    public boolean bookNotFound(Long idBook) {
        return repository.findById(idBook).isEmpty();
    }

}
