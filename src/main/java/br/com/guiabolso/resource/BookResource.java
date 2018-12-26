package br.com.guiabolso.resource;

import br.com.guiabolso.domain.Book;
import br.com.guiabolso.model.ReadBookPage;
import br.com.guiabolso.service.BookService;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author daniel
 */
@RestController
@RequestMapping("/books")
public class BookResource {

    @Autowired
    private BookService bookService;

    private final String urlToFindBooks = "https://kotlinlang.org/docs/books.html";

    /**
     * Persiste um livro novo na base.
     *
     * @param book
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> save(@RequestBody Book book) {
        book = bookService.save(book);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    /**
     * Obtém um livro a partir de seu id.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> find(@PathVariable("id") Integer id) {
        Book book = bookService.findOne(id);

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    /**
     * Obtém uma lista de livros a partir de uma página HTML.
     *
     * @return
     */
    @Cacheable("books")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Book>> findAll() {

        List<Book> books = new ArrayList<>();

        try {
            ReadBookPage rbp = new ReadBookPage(urlToFindBooks);
            books = rbp.getBooks();

        } catch (IOException ex) {
            Logger.getLogger(BookResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

}
