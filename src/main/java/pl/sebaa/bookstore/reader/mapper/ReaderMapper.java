package pl.sebaa.bookstore.reader.mapper;

import org.springframework.stereotype.Service;
import pl.sebaa.bookstore.reader.domain.ReaderDto;
import pl.sebaa.bookstore.reader.model.Reader;

@Service
public class ReaderMapper {

    public Reader mapToReader(final ReaderDto readerDto) {
        return new Reader().builder()
                .name(readerDto.getName())
                .lastname(readerDto.getLastname())
                .build();
    }

    public ReaderDto mapToReaderDto(final Reader reader) {
        return new ReaderDto(
                reader.getId(),
                reader.getName(),
                reader.getLastname(),
                reader.getCreateAccount()
        );
    }
}
