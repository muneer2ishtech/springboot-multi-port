package fi.ishtech.practice.springboot.multiport.service;

import java.util.List;
import java.util.Optional;

import fi.ishtech.practice.springboot.multiport.entity.Book;

/**
 *
 * @author Muneer Ahmed Syed
 */
public interface BookService {

	List<Book> findAll();

	Optional<Book> findById(Long id);

	Book create(Book book);

	Book update(Book book);

	void deleteById(Long id);

}
