package pl.sebaa.bookstore.storage.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.sebaa.bookstore.storage.model.BookStatus;

@AllArgsConstructor
@Data
public class StorageDto {

    private Long id;
    private Long idBook;
    private BookStatus status;
}
