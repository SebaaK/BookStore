package pl.sebaa.bookstore.storage.mapper;

import org.springframework.stereotype.Service;
import pl.sebaa.bookstore.storage.domain.StorageDto;
import pl.sebaa.bookstore.storage.model.Storage;

@Service
public class StorageMapper {

    public StorageDto mapToStorageDto(final Storage storage) {
        return new StorageDto(
                storage.getId(),
                storage.getBook().getId(),
                storage.getStatus()
        );
    }
}
