package fi.ishtech.practice.springboot.booksapp.controller;

import java.util.NoSuchElementException;

import jakarta.validation.ConstraintViolationException;

import org.apache.commons.lang3.Strings;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

		if (Strings.CI.contains(ex.getMessage(), "Unique index or primary key violation")
				&& Strings.CI.contains(ex.getMessage(), "uk_book_title_author")) {
			log.warn("handle DataIntegrityViolationException: {}", ex.getMessage());

			return ResponseEntity.badRequest().body("Book title and author combination already exists");
		}

		log.error("handle DataIntegrityViolationException", ex);

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
		log.warn("handle IllegalArgumentException: {}", ex.getMessage());

		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
		log.warn("handle ConstraintViolationException: {}", ex.getMessage());

		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
		log.warn("handle NoSuchElementException: {}", ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

}