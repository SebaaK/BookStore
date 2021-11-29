package pl.sebaa.bookstore.book.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookDto {

    private Long id;
    private String title;
    private String author;
    private LocalDate publicationDate;
}
