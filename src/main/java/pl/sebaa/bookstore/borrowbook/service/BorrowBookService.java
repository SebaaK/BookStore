package pl.sebaa.bookstore.borrowbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.sebaa.bookstore.borrowbook.mapper.BorrowBookMapper;
import pl.sebaa.bookstore.borrowbook.model.BorrowBook;
import pl.sebaa.bookstore.borrowbook.repository.BorrowBookRepository;
import pl.sebaa.bookstore.reader.model.Reader;
import pl.sebaa.bookstore.reader.service.ReaderService;
import pl.sebaa.bookstore.storage.model.BookStatus;
import pl.sebaa.bookstore.storage.model.Storage;
import pl.sebaa.bookstore.storage.service.StorageService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowBookService {

    private final BorrowBookRepository repository;
    private final ReaderService readerService;
    private final StorageService storageService;
    private final BorrowBookMapper mapper;

    @Transactional
    public BorrowBook borrowBook(Long idReader, Long idBook) {
        Reader reader = readerService.getReaderById(idReader);

        List<Storage> booksByStatus = storageService.getAvailableBooksList(idBook, BookStatus.FREE);
        long countAvailableBooks = booksByStatus.size();
        if (countAvailableBooks < 1)
            throw new IllegalArgumentException("Not found available books");

        Storage storage = booksByStatus.get(0);

        BorrowBook borrowBook = new BorrowBook();
        borrowBook.setReader(reader);
        borrowBook.setStorage(storage);

        reader.addNewBorrowBook(borrowBook);

        storage.setStatus(BookStatus.BORROW);

        return repository.save(borrowBook);
    }

    @Transactional
    public BorrowBook returnBook(Long idReader, Long idBook, String status) {
        Reader reader = readerService.getReaderById(idReader);
        BookStatus bookStatus = BookStatus.FREE;
        if (status != null) {
            bookStatus = storageService.getBookStatusFormString(status);
        }

        BorrowBook borrowBook = reader.getBorrowBooks().stream()
                .filter(book1 -> book1.getStorage().getId() == idBook)
                .findFirst().orElseThrow();

        borrowBook.setReturningDate(LocalDate.now());
        borrowBook.getStorage().setStatus(bookStatus);
        reader.getBorrowBooks().remove(borrowBook);

        return borrowBook;
    }
}
