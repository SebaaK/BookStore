package pl.sebaa.bookstore.reader.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sebaa.bookstore.reader.model.Reader;

@Repository
public interface ReaderRepository extends CrudRepository<Reader, Long> {

}
