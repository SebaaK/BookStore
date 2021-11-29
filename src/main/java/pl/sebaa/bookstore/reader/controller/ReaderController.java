package pl.sebaa.bookstore.reader.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sebaa.bookstore.reader.domain.ReaderDto;
import pl.sebaa.bookstore.reader.mapper.ReaderMapper;
import pl.sebaa.bookstore.reader.model.Reader;
import pl.sebaa.bookstore.reader.service.ReaderService;

@RestController
@RequestMapping("/api/reader")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService service;
    private final ReaderMapper mapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createReader(@RequestBody ReaderDto readerDto) {
        Reader reader = mapper.mapToReader(readerDto);
        return new ResponseEntity(
                mapper.mapToReaderDto(service.addReader(reader)),
                HttpStatus.CREATED
        );
    }
}
