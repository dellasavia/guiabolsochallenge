package br.com.guiabolso.repository;

import br.com.guiabolso.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author daniel
 */
public interface BookRepository extends JpaRepository<Book, Integer> {

}
