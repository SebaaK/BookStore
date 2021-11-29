package pl.sebaa.bookstore.book.mapper;

import org.springframework.stereotype.Service;
import pl.sebaa.bookstore.book.domain.BookDto;
import pl.sebaa.bookstore.book.model.Book;

@Service
public class BookMapper {

    public static Book bookDtoToBook(BookDto dto) {
        return new Book().builder()
                .author(dto.getAuthor())
                .title(dto.getTitle())
                .publicationDate(dto.getPublicationDate())
                .build();
    }
}
