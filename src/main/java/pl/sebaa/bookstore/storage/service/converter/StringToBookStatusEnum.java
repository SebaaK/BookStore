package pl.sebaa.bookstore.storage.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.sebaa.bookstore.storage.model.BookStatus;

@Component
public class StringToBookStatusEnum implements Converter<String, BookStatus> {

    @Override
    public BookStatus convert(String source) {
        if(source == null)
            return null;

        try {
            return BookStatus.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
