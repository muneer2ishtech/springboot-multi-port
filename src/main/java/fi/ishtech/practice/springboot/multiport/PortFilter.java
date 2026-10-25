package fi.ishtech.practice.springboot.multiport;

import java.io.IOException;

import org.apache.commons.lang3.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Slf4j
@Component
@Order(1)
public class PortFilter implements Filter {

	@Value("${fi.ishtech.practice.springboot.multiport.additional-ports:false}")
	private boolean additionalPorts;

	@Value("${fi.ishtech.practice.springboot.multiport.user-port:}")
	private Integer userPort;

	@Value("${fi.ishtech.practice.springboot.multiport.book-port:}")
	private Integer bookPort;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		log.debug("PORT:{}, URI:{}", req.getLocalPort(), req.getRequestURI());

		if (this.additionalPorts) {
			if (Strings.CI.contains(req.getRequestURI(), "/books")) {
				if (this.bookPort != req.getLocalPort()) {
					((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST,
							"Invalid Port " + req.getLocalPort() + ", use " + this.bookPort);
					return;
				}
			}
			if (Strings.CI.contains(req.getRequestURI(), "/users")) {
				if (this.userPort != req.getLocalPort()) {
					((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST,
							"Invalid Port " + req.getLocalPort() + ", use " + this.userPort);
					return;
				}
			}
		}

		fc.doFilter(request, response);
	}

}