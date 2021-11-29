package pl.sebaa.bookstore.borrowbook.model;

import lombok.*;
import pl.sebaa.bookstore.reader.model.Reader;
import pl.sebaa.bookstore.storage.model.Storage;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "borrow_book")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;
    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;
    private LocalDate borrowDate;
    private LocalDate returningDate;

    @PrePersist
    void borrowDate() {
        borrowDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return "BorrowBook{" +
                "id=" + id +
                ", borrowDate=" + borrowDate +
                ", returningDate=" + returningDate +
                '}';
    }
}
