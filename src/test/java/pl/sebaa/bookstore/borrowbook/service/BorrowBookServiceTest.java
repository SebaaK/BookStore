package pl.sebaa.bookstore.borrowbook.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.sebaa.bookstore.book.model.Book;
import pl.sebaa.bookstore.book.repository.BookRepository;
import pl.sebaa.bookstore.borrowbook.domain.BorrowBookDto;
import pl.sebaa.bookstore.borrowbook.model.BorrowBook;
import pl.sebaa.bookstore.borrowbook.repository.BorrowBookRepository;
import pl.sebaa.bookstore.reader.model.Reader;
import pl.sebaa.bookstore.reader.repository.ReaderRepository;
import pl.sebaa.bookstore.storage.model.BookStatus;
import pl.sebaa.bookstore.storage.model.Storage;
import pl.sebaa.bookstore.storage.repository.StorageRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BorrowBookServiceTest {

    private BorrowBookService service;
    private BorrowBookRepository borrowBookRepository;
    private ReaderRepository readerRepository;
    private BookRepository bookRepository;
    private StorageRepository storageRepository;

    @Autowired
    public BorrowBookServiceTest(BorrowBookService service, BorrowBookRepository borrowBookRepository, ReaderRepository readerRepository, BookRepository bookRepository, StorageRepository storageRepository) {
        this.service = service;
        this.borrowBookRepository = borrowBookRepository;
        this.readerRepository = readerRepository;
        this.bookRepository = bookRepository;
        this.storageRepository = storageRepository;
    }

    private Reader temporaryReader = new Reader(null, "Jan", "Kowalski", null, new ArrayList<>());
    private Book temporaryBook = new Book(null, "NowyTytuł", "NowyAutor", LocalDate.now());
    private Storage temporaryStorage;

    @Transactional
    @BeforeAll
    void addData() {
        temporaryReader = readerRepository.save(temporaryReader);
        temporaryBook = bookRepository.save(temporaryBook);
        Storage saveStorage = new Storage(null, temporaryBook, BookStatus.FREE);
        temporaryStorage = storageRepository.save(saveStorage);
    }

    @AfterAll
    void deleteData() {
        storageRepository.deleteById(temporaryStorage.getId());
        readerRepository.deleteById(temporaryReader.getId());
        bookRepository.deleteById(temporaryBook.getId());
    }

    @Test
    void borrowBook() {
        //given
        //when
        ResponseEntity responseEntity = service.borrowBook(temporaryReader.getId(), temporaryBook.getId());
        BorrowBookDto bodyResponse = (BorrowBookDto) responseEntity.getBody();

        //then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(temporaryReader.getId(), bodyResponse.getIdReader());
        assertEquals(temporaryStorage.getId(), bodyResponse.getIdStorage());
    }

    @Test
    void borrowBookIsNotPossible() {
        //given
        temporaryStorage.setStatus(BookStatus.BORROW);
        storageRepository.save(temporaryStorage);
        //when
        ResponseEntity responseEntity = service.borrowBook(temporaryReader.getId(), temporaryBook.getId());

        //then
        assertEquals(HttpStatus.PARTIAL_CONTENT, responseEntity.getStatusCode());
        assertEquals("This book is not available.",  responseEntity.getBody());
    }

    @Test
    void returnBook() {
        //given
        BorrowBook borrowBook = new BorrowBook(null, temporaryStorage, temporaryReader, LocalDate.now(), null);
        borrowBook = borrowBookRepository.save(borrowBook);

        temporaryReader.addNewBorrowBook(borrowBook);
        temporaryReader = readerRepository.save(temporaryReader);

        temporaryStorage.setStatus(BookStatus.BORROW);
        temporaryStorage = storageRepository.save(temporaryStorage);

        //when
        ResponseEntity responseEntity = service.returnBook(temporaryReader.getId(), temporaryBook.getId(), null);
        BorrowBookDto body = (BorrowBookDto) responseEntity.getBody();

        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(temporaryReader.getId(), body.getIdReader());
        assertEquals(temporaryStorage.getId(), body.getIdStorage());
        assertNotNull(body.getReturningDate());
    }
}