package pl.sebaa.bookstore.borrowbook.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class BorrowBookDto {
    private Long id;
    private Long idStorage;
    private Long idReader;
    private LocalDate borrowDate;
    private LocalDate returningDate;
}
