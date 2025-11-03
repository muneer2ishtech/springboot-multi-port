package fi.ishtech.practice.springboot.multiport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fi.ishtech.practice.springboot.multiport.entity.User;
import fi.ishtech.practice.springboot.multiport.security.jwt.JwtUtil;
import fi.ishtech.practice.springboot.multiport.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@RestController
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/api/v1/users")
	public ResponseEntity<Void> findAll() {
		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
	}

	@PreAuthorize(value = "hasAuthority('ROLE_USER')" + " and authentication.principal.id.equals(#id) ")
	@GetMapping("/api/v1/users/{id}")
	public User findById(@PathVariable Long id) {
		return userService.findById(id);
	}

	@PreAuthorize(value = "hasAuthority('ROLE_USER')")
	@PutMapping("/api/v1/users")
	public ResponseEntity<?> update(@Valid @RequestBody User user) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long userId = jwtUtil.getUserId(authentication);
		String username = jwtUtil.getUsername(authentication);
		log.debug("Updating User({})", user.getId());

		Assert.notNull(user.getId(), "User id is mandatory to find and update details");
		Assert.isTrue(userId.equals(user.getId()), "Cannot update details of another User");
		Assert.isTrue(username.equalsIgnoreCase(user.getEmail()) || !StringUtils.hasText(user.getEmail()),
				"Cannot update username/email");
		Assert.isTrue(!StringUtils.hasText(user.getPasswordHash()), "Cannot update password using this API");

		user = userService.update(user);

		return ResponseEntity.ok(user);
	}

}
