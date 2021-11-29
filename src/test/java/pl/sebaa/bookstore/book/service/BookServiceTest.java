package pl.sebaa.bookstore.book.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sebaa.bookstore.book.model.Book;
import pl.sebaa.bookstore.book.repository.BookRepository;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookServiceTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookService service;

    @Test
    void addNewTitle() {
        //given
        LocalDate now = LocalDate.now();
        Book book = new Book(null, "Test tytuł", "Test autor", now);
        //when
        Book saveBook = service.addNewTitle(book);
        Long id = saveBook.getId();

        //then
        assertEquals(id, saveBook.getId());
        assertEquals("Test tytuł", saveBook.getTitle());
        assertEquals("Test autor", saveBook.getAuthor());
        assertEquals(now, saveBook.getPublicationDate());

        repository.deleteById(id);
    }

    @Test
    void getBookById() {
        //given
        Book saveBook = repository.save(
                new Book(
                        null,
                        "NewBook",
                        "NewAuthor",
                        LocalDate.now()
                ));
        Long id = saveBook.getId();

        //when
        Book resultBook = service.getBookById(id);

        //then
        assertEquals(id, resultBook.getId());
        assertEquals(saveBook.getTitle(), resultBook.getTitle());
        assertEquals(saveBook.getAuthor(), resultBook.getAuthor());
        assertEquals(saveBook.getPublicationDate(), resultBook.getPublicationDate());
    }

    @Test
    void bookByIdThrowsException() {
        //given

        //when

        //then
        assertThrows(NoSuchElementException.class, () -> service.getBookById(Long.MAX_VALUE));

    }

    @Test
    void bookNotFound() {
        //given

        //when
        boolean bookNotFound = service.bookNotFound(Long.MAX_VALUE);

        //then
        assertFalse(bookNotFound);

    }
}