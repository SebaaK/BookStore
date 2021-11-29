package pl.sebaa.bookstore.borrowbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sebaa.bookstore.borrowbook.service.BorrowBookService;

@RestController
@RequestMapping("/api/borrow")
@RequiredArgsConstructor
public class BorrowBookController {

    private final BorrowBookService service;

    @PostMapping(value = "/{idReader}/book/{idBook}")
    public ResponseEntity borrowBook(@PathVariable("idReader") Long idReader, @PathVariable("idBook") Long idBook) {
        if (idReader == null || idReader < 1)
            return new ResponseEntity(
                    "Bad request for idReader replace and try again",
                    HttpStatus.BAD_REQUEST
            );

        if (idBook == null || idBook < 1)
            return new ResponseEntity(
                    "Bad request for idBook replace and try again",
                    HttpStatus.BAD_REQUEST
            );

        return service.borrowBook(idReader, idBook);
    }

    @PutMapping("/{idReader}/book/{idBook}")
    public ResponseEntity returnBook(@PathVariable("idReader") Long idReader, @PathVariable("idBook") Long idBook, @RequestParam(value = "status", required = false) String status) {
        if (idReader == null || idReader < 1)
            return new ResponseEntity(
                    "Bad request for idReader replace and try again",
                    HttpStatus.BAD_REQUEST
            );

        if (idBook == null || idBook < 1)
            return new ResponseEntity(
                    "Bad request for idBook replace and try again",
                    HttpStatus.BAD_REQUEST
            );

        return service.returnBook(idReader, idBook, status);
    }
}
