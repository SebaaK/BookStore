package pl.sebaa.bookstore.reader.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

    private final ReaderService readerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Reader createReader(@RequestBody ReaderDto readerDto) {
        Reader reader = ReaderMapper.mapToReader(readerDto);
        return readerService.addReader(reader);
    }
}
