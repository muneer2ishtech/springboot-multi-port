package fi.ishtech.practice.springboot.multiport;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import jakarta.servlet.ServletException;

/**
 *
 * @author Muneer Ahmed Syed
 */
class PortFilterTest {

	private static final int DEFAULT_PORT = 8080;
	private static final int BOOK_PORT = 8081;
	private static final int USER_PORT = 8082;

	private PortFilter portFilter;

	@BeforeEach
	void setUp() {
		portFilter = new PortFilter();
		ReflectionTestUtils.setField(portFilter, "additionalPorts", true);
		ReflectionTestUtils.setField(portFilter, "bookPort", BOOK_PORT);
		ReflectionTestUtils.setField(portFilter, "userPort", USER_PORT);
	}

	@ParameterizedTest
	@CsvSource({ "/api/v1/books, 8081", "/api/v1/books/1, 8081", "/api/v1/users, 8082", "/api/v1/users/1, 8082" })
	void testDoFilter_rightPort_allowed(String uri, int port) throws IOException, ServletException {
		MockFilterChain chain = new MockFilterChain();
		MockHttpServletResponse response = doFilter(uri, port, chain);

		assertThat(chain.getRequest()).isNotNull();
		assertThat(response.getStatus()).isEqualTo(200);
	}

	@ParameterizedTest
	@CsvSource({ "/api/v1/books, 8080, 8081", "/api/v1/books/1, 8082, 8081", "/api/v1/users, 8080, 8082",
			"/api/v1/users/1, 8081, 8082" })
	void testDoFilter_wrongPort_badRequest(String uri, int port, int correctPort) throws IOException, ServletException {
		MockFilterChain chain = new MockFilterChain();
		MockHttpServletResponse response = doFilter(uri, port, chain);

		assertThat(chain.getRequest()).isNull();
		assertThat(response.getStatus()).isEqualTo(400);
		assertThat(response.getErrorMessage()).isEqualTo("Invalid Port " + port + ", use " + correctPort);
	}

	@ParameterizedTest
	@CsvSource({ "/api/v1/auth/signin, 8080", "/api/v1/auth/signin, 8081", "/api/v1/auth/signin, 8082",
			"/actuator/health, 8080", "/actuator/health, 8081" })
	void testDoFilter_otherEndpoint_notRestricted(String uri, int port) throws IOException, ServletException {
		MockFilterChain chain = new MockFilterChain();
		MockHttpServletResponse response = doFilter(uri, port, chain);

		assertThat(chain.getRequest()).isNotNull();
		assertThat(response.getStatus()).isEqualTo(200);
	}

	@Test
	void testDoFilter_additionalPortsDisabled_notRestricted() throws IOException, ServletException {
		ReflectionTestUtils.setField(portFilter, "additionalPorts", false);

		MockFilterChain chain = new MockFilterChain();
		MockHttpServletResponse response = doFilter("/api/v1/books", DEFAULT_PORT, chain);

		assertThat(chain.getRequest()).isNotNull();
		assertThat(response.getStatus()).isEqualTo(200);
	}

	private MockHttpServletResponse doFilter(String uri, int port, MockFilterChain chain)
			throws IOException, ServletException {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", uri);
		request.setLocalPort(port);
		MockHttpServletResponse response = new MockHttpServletResponse();

		portFilter.doFilter(request, response, chain);

		return response;
	}

}