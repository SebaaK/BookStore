package pl.sebaa.bookstore.storage.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.sebaa.bookstore.book.model.Book;
import pl.sebaa.bookstore.book.repository.BookRepository;
import pl.sebaa.bookstore.storage.model.BookStatus;
import pl.sebaa.bookstore.storage.model.Storage;
import pl.sebaa.bookstore.storage.repository.StorageRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StorageServiceTest {

    private StorageService service;
    private StorageRepository repository;
    private BookRepository bookRepository;

    @Autowired
    public StorageServiceTest(StorageService service, StorageRepository repository, BookRepository bookRepository) {
        this.service = service;
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    private Book book = new Book(null, "TestTytuł", "TestAutor", LocalDate.of(1996, 05, 14));

    @BeforeAll
    void insertData() {
        book = bookRepository.save(book);
    }

    @AfterAll
    void deleteData() {
        bookRepository.deleteById(book.getId());
    }

    @Test
    void newBookToStorage() {
        //given
        //when
        Storage result = service.newBookToStorage(book.getId());
        Book resultBook = result.getBook();

        //then
        assertNotNull(result.getId());
        assertEquals(BookStatus.FREE, result.getStatus());
        assertAll( "Check book object",
                () -> assertEquals(book.getId(), resultBook.getId()),
                () -> assertEquals(book.getTitle(), resultBook.getTitle()),
                () -> assertEquals(book.getAuthor(), resultBook.getAuthor()),
                () -> assertEquals(book.getPublicationDate(), resultBook.getPublicationDate())
        );
    }

    @Test
    void storageNotFound() {
        //given
        //when
        boolean result = service.storageNotFound(Long.MAX_VALUE);

        //then
        assertTrue(result);
    }

    @Test
    void storageIsFound() {
        //given
        Storage storage = new Storage(null, book, BookStatus.FREE);
        storage = repository.save(storage);

        //when
        boolean result = service.storageNotFound(storage.getId());

        //then
        assertFalse(result);

        //clear
        repository.deleteById(storage.getId());
    }

    @Test
    void getBookStatusFormString() {
        //given
        String dataToTest = "free";

        //when
        BookStatus result = service.getBookStatusFormString(dataToTest);

        //then
        assertEquals(BookStatus.FREE, result);
    }

    @Test
    void failedGetStatusFromString() {
        //given
        String dataToTest = "no-data";

        //when
        BookStatus result = service.getBookStatusFormString(dataToTest);

        //then
        assertNull(result);
    }

    @Test
    void storeAndStatusIsOk() {
        //given
        Storage storage = new Storage(null, book, BookStatus.FREE);
        storage = repository.save(storage);

        //when
        ResponseEntity result = service.storeAndStatusIsOk(storage.getId(), "FREE");

        //then
        assertNull(result);

        //clear
        repository.deleteById(storage.getId());
    }

    @Test
    void statusIsFailedInCheckingStatus() {
        //given
        Storage storage = new Storage(null, book, BookStatus.FREE);
        storage = repository.save(storage);

        //when
        ResponseEntity result = service.storeAndStatusIsOk(storage.getId(), "bad-status");

        //then
        assertEquals("This status is not find on system.", result.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, result.getStatusCode());

        //clear
        repository.deleteById(storage.getId());
    }

    @Test
    void storageIsFailedInCheckingStatus() {
        //given
        //when
        ResponseEntity result = service.storeAndStatusIsOk(Long.MAX_VALUE, "free");

        //then
        assertEquals("This storage is not exist.", result.getBody());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void bookIsOk() {
        //given
        //when
        ResponseEntity result = service.bookIsOk(book.getId());

        //then
        assertNull(result);
    }

    @Test
    @DisplayName("Book not found on method: bookIsOk()")
    void bookIsNotFound() {
        //given
        //when
        ResponseEntity result = service.bookIsOk(Long.MAX_VALUE);

        //then
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    @DisplayName("Book id transferred to method: bookIsOk() is negative number")
    void idIsNotPositiveNumber() {
        //given
        //when
        ResponseEntity result = service.bookIsOk(Long.MIN_VALUE);

        //then
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void changeStatusIsPassed() {
        //given
        Storage storageTest = new Storage(null, book, BookStatus.FREE);
        storageTest = repository.save(storageTest);

        //when
        Storage result = service.changeStatus(storageTest.getId(), "borrow");
        Book resultBook = result.getBook();

        //then
        assertNotNull(result.getId());
        assertAll( "Book object",
                () -> assertNotNull(resultBook.getId()),
                () -> assertEquals("TestTytuł", resultBook.getTitle()),
                () -> assertEquals("TestAutor", resultBook.getAuthor()),
                () -> assertEquals(LocalDate.of(1996, 05, 14), resultBook.getPublicationDate())
        );
        assertEquals(BookStatus.BORROW, result.getStatus());

        //clear
        repository.deleteById(storageTest.getId());
    }

    @Test
    void getAvailableBooksList() {
        //given
        repository.deleteAll();
        Storage storage = new Storage(null, book, BookStatus.FREE);
        storage = repository.save(storage);

        //when
        int resultList = service.getAvailableBooksList(book.getId(), BookStatus.FREE).size();

        //then
        assertEquals(1, resultList);
    }

    @Test
    void getAvailableBooksListFailed() {
        //given
        repository.deleteAll();

        //when
        int resultList = service.getAvailableBooksList(book.getId(), BookStatus.FREE).size();

        //then
        assertEquals(0, resultList);
    }
}