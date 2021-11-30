package pl.sebaa.bookstore.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sebaa.bookstore.storage.mapper.StorageMapper;
import pl.sebaa.bookstore.storage.model.BookStatus;
import pl.sebaa.bookstore.storage.service.StorageService;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService service;
    private final StorageMapper mapper;

    @PostMapping(value = "/addBook/{idBook}" )
    public ResponseEntity andNewBookToStorage(@PathVariable("idBook") long idBook) {
        ResponseEntity response = service.bookIsOk(idBook);
        if (response != null)
            return response;

        return new ResponseEntity(
                mapper.mapToStorageDto(service.newBookToStorage(idBook)),
                HttpStatus.CREATED
        );
    }

    @PutMapping(value = "/{idStorage}")
    public ResponseEntity changeStatusBookOnStorage(@PathVariable("idStorage") long idStorage, @RequestParam("status") String status) {
        ResponseEntity response = service.storeAndStatusIsOk(idStorage, status);
        if (response != null)
            return response;

        return new ResponseEntity(
                mapper.mapToStorageDto(service.changeStatus(idStorage, status)),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/{idBook}")
    public ResponseEntity getAvailableStorageBook(@PathVariable("idBook") long idBook, @RequestParam(value = "status", required = false) String status) {
        BookStatus findStatus = BookStatus.FREE;
        if(status != null)
            if(service.statusIsOk(status))
                findStatus = service.getBookStatusFormString(status);
            else
                return new ResponseEntity("This status is not find on system.", HttpStatus.BAD_REQUEST);

        return new ResponseEntity(
                service.getAvailableBooksList(idBook, findStatus).size(),
                HttpStatus.OK
        );
    }
}
