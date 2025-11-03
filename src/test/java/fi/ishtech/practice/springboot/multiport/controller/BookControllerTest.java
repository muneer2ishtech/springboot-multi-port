package fi.ishtech.practice.springboot.multiport.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import fi.ishtech.practice.springboot.multiport.entity.Book;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class BookControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	@Order(1)
	@WithMockUser(username = "junit@ishtech.fi", password = "Test#123", authorities = "ROLE_USER")
	public void testCreateNewBookSuccess() throws Exception {
		Book book = new Book();
		book.setTitle("Intro to Java");
		book.setAuthor("Muneer");
		book.setYear(Short.valueOf("2023"));
		book.setPrice(new BigDecimal("12.34"));

		Gson gson = new Gson();
		String requestJson = gson.toJson(book);

		// @formatter:off
 		mvc.perform(post("/api/v1/books")
 				.contentType(MediaType.APPLICATION_JSON)
 				.content(requestJson))
 			.andExpect(status().isCreated());
		// @formatter:on
	}

	@Test
	@Order(2)
	@WithMockUser(username = "junit@ishtech.fi", password = "Test#123", authorities = "ROLE_USER")
	public void testFindAllSuccess() throws Exception {
		// @formatter:off
 		mvc.perform(get("/api/v1/books")
 				.contentType(MediaType.APPLICATION_JSON))
 			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].title", is("Intro to Java")));
		// @formatter:on
	}

	@Test
	@Order(3)
	@WithMockUser(username = "junit@ishtech.fi", password = "Test#123", authorities = "ROLE_USER")
	public void testFindByIdSuccess() throws Exception {
		log.debug("Testing findById for existing");
		// @formatter:off
 		mvc.perform(get("/api/v1/books/1")
 				.contentType(MediaType.APPLICATION_JSON))
 			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title", is("Intro to Java")));
		// @formatter:on
	}

	@Test
	@Order(4)
	@WithMockUser(username = "junit@ishtech.fi", password = "Test#123", authorities = "ROLE_USER")
	public void testFindByIdFail() throws Exception {
		// @formatter:off
 		mvc.perform(get("/api/v1/books/2")
 				.contentType(MediaType.APPLICATION_JSON))
 			.andExpect(status().isNotFound());
		// @formatter:on
	}

	@Test
	@Order(5)
	@WithMockUser(username = "junit@ishtech.fi", password = "Test#123", authorities = "ROLE_USER")
	public void testCreateNewBookFailForMissingData() throws Exception {
		Book book = new Book();
		book.setAuthor("Muneer");
		book.setYear(Short.valueOf("2023"));
		book.setPrice(new BigDecimal("12.34"));

		Gson gson = new Gson();
		String requestJson = gson.toJson(book);

		// @formatter:off
 		mvc.perform(post("/api/v1/books")
 				.contentType(MediaType.APPLICATION_JSON)
 				.content(requestJson))
 			.andExpect(status().isBadRequest());
		// @formatter:on
	}

	@Test
	@Order(6)
	@WithMockUser(username = "junit@ishtech.fi", password = "Test#123", authorities = "ROLE_USER")
	public void testCreateNewBookFailForNonUniqueTitle() throws Exception {
		Book book = new Book();
		book.setTitle("Intro to Java");
		book.setAuthor("Muneer");
		book.setYear(Short.valueOf("2023"));
		book.setPrice(new BigDecimal("12.34"));

		Gson gson = new Gson();
		String requestJson = gson.toJson(book);

		// @formatter:off
 		mvc.perform(post("/api/v1/books")
 				.contentType(MediaType.APPLICATION_JSON)
 				.content(requestJson))
 			.andExpect(status().isBadRequest());
		// @formatter:on
	}

	@Test
	@Order(7)
	@WithMockUser(username = "junit@ishtech.fi", password = "Test#123", authorities = "ROLE_USER")
	public void testUpdateBookOk() throws Exception {
		Book book = new Book();
		book.setId(1L);
		book.setTitle("Intro to Java");
		book.setAuthor("Muneer");
		book.setYear(Short.valueOf("2023"));
		book.setPrice(new BigDecimal("56.78"));

		Gson gson = new Gson();
		String requestJson = gson.toJson(book);

		// @formatter:off
 		mvc.perform(put("/api/v1/books")
 				.contentType(MediaType.APPLICATION_JSON)
 				.content(requestJson))
 			.andExpect(status().isOk());
		// @formatter:on
	}

	@Test
	@Order(8)
	@WithMockUser(username = "junit@ishtech.fi", password = "Test#123", authorities = "ROLE_USER")
	public void testUpdateBookFailForMissingId() throws Exception {
		Book book = new Book();
		book.setTitle("Intro to Java");
		book.setAuthor("Muneer");
		book.setYear(Short.valueOf("2023"));
		book.setPrice(new BigDecimal("56.78"));

		Gson gson = new Gson();
		String requestJson = gson.toJson(book);

		// @formatter:off
 		mvc.perform(put("/api/v1/books")
 				.contentType(MediaType.APPLICATION_JSON)
 				.content(requestJson))
 			.andExpect(status().isBadRequest());
		// @formatter:on
	}

}
