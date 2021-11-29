package pl.sebaa.bookstore.storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.sebaa.bookstore.book.model.Book;
import pl.sebaa.bookstore.book.service.BookService;
import pl.sebaa.bookstore.storage.model.BookStatus;
import pl.sebaa.bookstore.storage.model.Storage;
import pl.sebaa.bookstore.storage.repository.StorageRepository;
import pl.sebaa.bookstore.storage.service.converter.StringToBookStatusEnum;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageRepository repository;
    private final BookService bookService;
    private final StringToBookStatusEnum converter;

    public Storage newBookToStorage(Long idBook, String status) {
        Book book = bookService.getBookById(idBook);
        BookStatus statusBook = converter.convert(status);
        Storage storage = new Storage(null, book, statusBook);
        return repository.save(storage);
    }

    public boolean storageNotFound(Long idStorage) {
        return repository.findById(idStorage).isEmpty();
    }

    public boolean statusIsOk(String status) {
        if(converter.convert(status) == null)
            return false;
        return true;
    }

    public BookStatus getBookStatusFormString(String status) {
            return converter.convert(status);
    }

    public ResponseEntity storeAndStatusIsOk(Long idStorage, String status) {
        if(!statusIsOk(status))
            return new ResponseEntity<>("This status is not find on system.", HttpStatus.OK); //TODO: Za dużo razy wywołanie
        if(idStorage < 1 || storageNotFound(idStorage))
            return new ResponseEntity<>("This storage is not exist.", HttpStatus.NOT_FOUND);
        return null;
    }

    public ResponseEntity bookAndStatusIsOk(Long idBook, String status) {
        if(!statusIsOk(status))
            return new ResponseEntity<>("This status is not find on system.", HttpStatus.BAD_REQUEST);
        if(idBook < 1 || bookService.bookNotFound(idBook))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return null;
    }

    public Storage changeStatus(Long idStorage, String status) {
        Storage selectedStorage = repository.findById(idStorage).get();
        selectedStorage.setStatus(converter.convert(status));
        return repository.save(selectedStorage);
    }

    public List<Storage> getCountBooksByStatus(Long idBook, BookStatus status) {
        Book bookById = bookService.getBookById(idBook);
        return repository.countAvailableBooks(bookById, status);
    }
}
