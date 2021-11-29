package pl.sebaa.bookstore.reader.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReaderDto {

    private Long id;
    private String name;
    private String lastname;
    private LocalDateTime createAccount;
}
