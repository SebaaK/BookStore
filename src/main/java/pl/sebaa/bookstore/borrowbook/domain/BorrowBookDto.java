package pl.sebaa.bookstore.borrowbook.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.sebaa.bookstore.reader.model.Reader;
import pl.sebaa.bookstore.storage.model.Storage;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class BorrowBookDto {
    private Long id;
    private Storage storage;
    private Reader reader;
    private LocalDate borrowDate;
    private LocalDate returningDate;

    @Override
    public String toString() {
        return "BorrowBookDto{" +
                "id=" + id +
                '}';
    }
}
