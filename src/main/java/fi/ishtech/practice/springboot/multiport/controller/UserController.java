package fi.ishtech.practice.springboot.multiport.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fi.ishtech.springboot.jwtauth.dto.UserProfileDto;
import fi.ishtech.springboot.jwtauth.jwt.JwtService;
import fi.ishtech.springboot.jwtauth.service.UserProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserProfileService userProfileService;

	private final JwtService jwtService;

	@GetMapping("/api/v1/users")
	public ResponseEntity<Void> findAll() {
		return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
	}

	@PreAuthorize(value = "hasAuthority('ROLE_USER')" + " and authentication.principal.id.equals(#id) ")
	@GetMapping("/api/v1/users/{id}")
	public UserProfileDto findById(@PathVariable Long id) {
		return userProfileService.findByIdAndMapToDto(id);
	}

	@PreAuthorize(value = "hasAuthority('ROLE_USER')")
	@PutMapping("/api/v1/users")
	public ResponseEntity<?> update(@RequestBody @Valid UserProfileDto user, HttpServletRequest request) {
		Long userId = jwtService.extractUserIdFromRequest(request);
		log.debug("Updating User({})", user.getId());

		Assert.notNull(user.getId(), "User id is mandatory to find and update details");
		Assert.isTrue(userId.equals(user.getId()), "Cannot update details of another User");

		var result = userProfileService.updateAndMapToDto(user);

		return ResponseEntity.ok(result);
	}

}