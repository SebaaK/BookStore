package pl.sebaa.bookstore.storage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sebaa.bookstore.storage.model.BookStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StorageDto {

    private Long id;
    private String idBook;
    private BookStatus status;
}
