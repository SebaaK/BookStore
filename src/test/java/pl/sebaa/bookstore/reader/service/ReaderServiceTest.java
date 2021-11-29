package pl.sebaa.bookstore.reader.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sebaa.bookstore.reader.model.Reader;
import pl.sebaa.bookstore.reader.repository.ReaderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReaderServiceTest {

    private ReaderService service;
    private ReaderRepository repository;

    @Autowired
    public ReaderServiceTest(ReaderService service, ReaderRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @Test
    void addReader() {
        //given
        Reader readerForTest = new Reader(null, "Jan", "Kowalski", LocalDateTime.now(), new ArrayList<>());

        //when
        Reader reader = service.addReader(readerForTest);

        //then
        assertNotNull(reader.getId());
        assertEquals("Jan", reader.getName());
        assertEquals("Kowalski", reader.getLastname());
        assertNotNull(reader.getCreateAccount());
        assertEquals(0, reader.getBorrowBooks().size());

        repository.deleteById(reader.getId());
    }

    @Test
    void getReaderById() {
        //given
        Reader readerForTest = new Reader(null, "Jan", "Kowalski", LocalDateTime.now(), new ArrayList<>());
        readerForTest = repository.save(readerForTest);
        Long id = readerForTest.getId();

        //when
        Reader result = service.getReaderById(id);

        //then
        assertEquals(id, result.getId());
        assertEquals("Jan", result.getName());
        assertEquals("Kowalski", result.getLastname());
        assertNotNull(result.getCreateAccount());

        repository.deleteById(id);
    }

    @Test
    void getReaderByIdIsNotExist() {
        //given
        //when
        //then
        assertThrows(NoSuchElementException.class, () -> service.getReaderById(Long.MAX_VALUE));
    }
}