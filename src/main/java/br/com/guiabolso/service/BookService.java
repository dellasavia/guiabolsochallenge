package br.com.guiabolso.service;

import br.com.guiabolso.domain.Book;
import br.com.guiabolso.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author daniel
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book findOne(Integer idBook) {
        return bookRepository.findOne(idBook);
    }

}
