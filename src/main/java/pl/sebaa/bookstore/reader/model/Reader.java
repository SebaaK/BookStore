package pl.sebaa.bookstore.reader.model;

import lombok.*;
import pl.sebaa.bookstore.borrowbook.model.BorrowBook;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    @Column(name = "create_account")
    private LocalDateTime createAccount;
    @OneToMany(mappedBy = "reader")
    private List<BorrowBook> borrowBooks = new ArrayList<>();

    @PrePersist
    void datestamp() {
        createAccount = LocalDateTime.now();
    }

    public void addNewBorrowBook(BorrowBook book) {
        borrowBooks.add(book);
    }
}
