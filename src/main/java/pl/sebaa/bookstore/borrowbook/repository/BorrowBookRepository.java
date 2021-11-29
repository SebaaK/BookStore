package pl.sebaa.bookstore.borrowbook.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sebaa.bookstore.borrowbook.model.BorrowBook;

@Repository
public interface BorrowBookRepository extends CrudRepository<BorrowBook, Long> {

}
