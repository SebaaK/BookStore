package pl.sebaa.bookstore.book.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sebaa.bookstore.book.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

}
