package pl.sebaa.bookstore.storage.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.sebaa.bookstore.book.model.Book;
import pl.sebaa.bookstore.storage.model.BookStatus;
import pl.sebaa.bookstore.storage.model.Storage;

import java.util.List;

@Repository
public interface StorageRepository extends CrudRepository<Storage, Long> {

    @Query("SELECT s FROM Storage s WHERE s.book=:book AND s.status=:status")
    List<Storage> countAvailableBooks(@Param("book") Book book, @Param("status") BookStatus status);
}
