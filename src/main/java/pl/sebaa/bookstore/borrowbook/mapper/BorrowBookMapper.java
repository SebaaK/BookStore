package pl.sebaa.bookstore.borrowbook.mapper;

import org.springframework.stereotype.Service;
import pl.sebaa.bookstore.borrowbook.domain.BorrowBookDto;
import pl.sebaa.bookstore.borrowbook.model.BorrowBook;

@Service
public class BorrowBookMapper {

    public BorrowBookDto mapToBorrowBookDto(final BorrowBook borrowBook) {
        return new BorrowBookDto(
                borrowBook.getId(),
                borrowBook.getStorage().getId(),
                borrowBook.getReader().getId(),
                borrowBook.getBorrowDate(),
                borrowBook.getReturningDate()
        );
    }
}
