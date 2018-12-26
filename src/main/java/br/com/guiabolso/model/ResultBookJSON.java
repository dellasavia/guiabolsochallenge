package br.com.guiabolso.model;

import br.com.guiabolso.domain.Book;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel.savia
 */
public class ResultBookJSON {

    private Integer numberBooks;
    private List<Book> books = new ArrayList<>();

    private ResultBookJSON() {
    }

    public static ResultBookJSON getInstance(List<Book> books) {
        ResultBookJSON result = new ResultBookJSON();
        result.setBooks(books);
        result.setNumberBooks(books.size());
        return result;
    }

    public Integer getNumberBooks() {
        return numberBooks;
    }

    public void setNumberBooks(Integer numberBooks) {
        this.numberBooks = numberBooks;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
