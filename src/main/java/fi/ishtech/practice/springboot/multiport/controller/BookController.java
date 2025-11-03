package fi.ishtech.practice.springboot.multiport.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fi.ishtech.practice.springboot.multiport.entity.Book;
import fi.ishtech.practice.springboot.multiport.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@RestController
@Slf4j
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping("/api/v1/books")
	public List<Book> findAll() {
		return bookService.findAll();

	}

	@GetMapping("/api/v1/books/{id}")
	public ResponseEntity<Book> findById(@PathVariable Long id) {
		return ResponseEntity.of(bookService.findById(id));
	}

	@PostMapping("/api/v1/books")
	public ResponseEntity<?> createNew(@Valid @RequestBody Book book) {
		log.debug("Creating new Book {}", book.getTitle());

		book = bookService.create(book);

		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/books/{bookId}")
				.buildAndExpand(book.getId()).toUri();

		return ResponseEntity.created(uri).body(book.getId());
	}

	@DeleteMapping("/api/v1/books/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		log.debug("Deleting Book({})", id);

		bookService.deleteById(id);

		return new ResponseEntity<Void>(HttpStatus.GONE);
	}

	@PutMapping("/api/v1/books")
	public ResponseEntity<?> update(@Valid @RequestBody Book book) {
		log.debug("Updating Book({})", book.getId());

		book = bookService.update(book);

		return ResponseEntity.ok(book);
	}

}
