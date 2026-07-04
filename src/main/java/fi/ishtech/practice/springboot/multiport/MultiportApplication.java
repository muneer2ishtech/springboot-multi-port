package fi.ishtech.practice.springboot.multiport;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.servlet.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

// @formatter:off
@SpringBootApplication(scanBasePackages = {
		"fi.ishtech.springboot.jwtauth",
		"fi.ishtech.practice.springboot.multiport",
		"fi.ishtech.practice.springboot.booksapp"
})
@EntityScan(basePackages = {
		"fi.ishtech.springboot.jwtauth.entity",
		"fi.ishtech.practice.springboot.booksapp.entity"
})
@EnableJpaRepositories(basePackages = {
		"fi.ishtech.springboot.jwtauth.repo",
		"fi.ishtech.practice.springboot.booksapp.repository"
})
// @formatter:on
@Slf4j
public class MultiportApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiportApplication.class, args);
	}

	@Value("${fi.ishtech.practice.springboot.multiport.additional-ports:false}")
	private boolean additionalPorts;

	@Value("${fi.ishtech.practice.springboot.multiport.user-port:}")
	private Integer userPort;

	@Value("${fi.ishtech.practice.springboot.multiport.book-port:}")
	private Integer bookPort;

	@Bean
	ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();

		Connector[] additionalConnectors = additionalConnectors();
		if (additionalConnectors != null && additionalConnectors.length != 0) {
			tomcat.addAdditionalConnectors(additionalConnectors);
		}

		return tomcat;
	}

	private Connector[] additionalConnectors() {
		log.info("additionalPorts:{}", this.additionalPorts);

		if (this.additionalPorts) {
			Assert.state(this.userPort != null, "Port for '**/users/**' cannot be null");
			Assert.state(this.bookPort != null, "Port for '**/books/**' cannot be null");

			log.info("PORT for **/users/**:{}", this.userPort);
			log.info("PORT for **/books/**:{}", this.bookPort);
		} else {
			return null;
		}

		List<Connector> connectors = new ArrayList<>();

		Connector connector1 = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector1.setScheme("http");
		connector1.setPort(this.userPort);
		connectors.add(connector1);

		Connector connector2 = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector2.setScheme("http");
		connector2.setPort(this.bookPort);
		connectors.add(connector2);

		return connectors.toArray(new Connector[] {});
	}

}