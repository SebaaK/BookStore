package pl.sebaa.bookstore.reader.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sebaa.bookstore.reader.model.Reader;
import pl.sebaa.bookstore.reader.repository.ReaderRepository;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository repository;

    public Reader addReader(Reader reader) {
        return repository.save(reader);
    }

    public Reader getReaderById(Long id) {
        return repository.findById(id).orElseThrow();
    }

}
