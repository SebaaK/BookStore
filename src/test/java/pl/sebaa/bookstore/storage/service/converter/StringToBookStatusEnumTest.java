package pl.sebaa.bookstore.storage.service.converter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sebaa.bookstore.storage.model.BookStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StringToBookStatusEnumTest {

    @Autowired
    private StringToBookStatusEnum converterEngine;

    @Test
    void convertToEnum() {
        //given
        List<String> stringList = Arrays.asList("free", null, "", "BoRrOw", "LOST");

        //when
        //then
        List<BookStatus> collect = stringList.stream()
                .map(s -> converterEngine.convert(s))
                .collect(Collectors.toList());

        assertEquals(BookStatus.FREE, collect.get(0));
        assertEquals(null, collect.get(1));
        assertEquals(null, collect.get(2));
        assertEquals(BookStatus.BORROW, collect.get(3));
        assertEquals(BookStatus.LOST, collect.get(4));
    }
}