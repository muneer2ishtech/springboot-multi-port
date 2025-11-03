package fi.ishtech.practice.springboot.multiport;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
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
			if (StringUtils.containsIgnoreCase(req.getRequestURI(), "/books")) {
				Assert.isTrue(this.bookPort == req.getLocalPort(),
						"Invalid Port " + req.getLocalPort() + ", use " + this.bookPort);
			}
			if (StringUtils.containsIgnoreCase(req.getRequestURI(), "/users")) {
				Assert.isTrue(this.userPort == req.getLocalPort(),
						"Invalid Port " + req.getLocalPort() + ", use " + this.userPort);
			}
		}

		fc.doFilter(request, response);
	}

}
