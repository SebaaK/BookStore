package pl.sebaa.bookstore.borrowbook.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sebaa.bookstore.book.model.Book;
import pl.sebaa.bookstore.book.repository.BookRepository;
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

import static org.junit.jupiter.api.Assertions.*;

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
    @Transactional
    void borrowBook() {
        //given
        temporaryReader.getBorrowBooks().clear();
        temporaryReader = readerRepository.save(temporaryReader);

        //when
        BorrowBook borrowBook = service.borrowBook(temporaryReader.getId(), temporaryBook.getId());
        Storage resultStorage = borrowBook.getStorage();
        Reader resultReader = borrowBook.getReader();

        //then
        assertNotNull(borrowBook.getId());
        assertAll("Checking Storage object",
                () -> assertEquals(temporaryStorage.getId(), resultStorage.getId()),
                () -> assertEquals(BookStatus.BORROW, resultStorage.getStatus())
        );
        assertAll("Checking Reader object",
                () -> assertEquals(temporaryReader.getId(), resultReader.getId()),
                () -> assertEquals(temporaryReader.getName(), resultReader.getName()),
                () -> assertEquals(temporaryReader.getLastname(), resultReader.getLastname()),
                () -> assertNotNull(resultReader.getCreateAccount()),
                () -> assertEquals(1, resultReader.getBorrowBooks().size())
        );
        assertNotNull(borrowBook.getBorrowDate());
        assertNull(borrowBook.getReturningDate());
    }

    @Test
    void borrowBookIsNotPossible() {
        //given
        temporaryStorage.setStatus(BookStatus.BORROW);
        storageRepository.save(temporaryStorage);
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> service.borrowBook(temporaryReader.getId(), temporaryBook.getId()));
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
        BorrowBook resultBorrowBook = service.returnBook(temporaryReader.getId(), temporaryBook.getId(), null);
        Storage resultStorage = resultBorrowBook.getStorage();
        Reader resultReader = resultBorrowBook.getReader();

        //then
        assertNotNull(resultBorrowBook.getId());
        assertAll("Checking Storage object",
                () -> assertEquals(temporaryStorage.getId(), resultStorage.getId()),
                () -> assertEquals(BookStatus.FREE, resultStorage.getStatus())
        );
        assertAll("Checking Reader object",
                () -> assertEquals(temporaryReader.getId(), resultReader.getId()),
                () -> assertEquals(temporaryReader.getName(), resultReader.getName()),
                () -> assertEquals(temporaryReader.getLastname(), resultReader.getLastname()),
                () -> assertNotNull(resultReader.getCreateAccount()),
                () -> assertEquals(0, resultReader.getBorrowBooks().size())
        );
        assertNotNull(resultBorrowBook.getBorrowDate());
        assertNotNull(resultBorrowBook.getReturningDate());
    }
}