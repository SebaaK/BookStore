package pl.sebaa.bookstore.borrowbook.mapper;

import pl.sebaa.bookstore.borrowbook.domain.BorrowBookDto;
import pl.sebaa.bookstore.borrowbook.model.BorrowBook;

public class BorrowBookMapper {

    public static BorrowBook mapToBorrowBook(final BorrowBookDto dto) {
        return new BorrowBook(
                dto.getId(),
                dto.getStorage(),
                dto.getReader(),
                dto.getBorrowDate(),
                dto.getReturningDate()
        );
    }

    public static BorrowBookDto mapToBorrowBookDto(final BorrowBook borrowBook) {
        return new BorrowBookDto(
                borrowBook.getId(),
                borrowBook.getStorage(),
                borrowBook.getReader(),
                borrowBook.getBorrowDate(),
                borrowBook.getReturningDate()
        );
    }
}
