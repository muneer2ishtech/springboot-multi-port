package fi.ishtech.practice.springboot.multiport.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fi.ishtech.practice.springboot.multiport.entity.User;
import fi.ishtech.practice.springboot.multiport.exception.UsernameAlreadyExistsException;
import fi.ishtech.practice.springboot.multiport.payload.PasswordUpdateRequest;
import fi.ishtech.practice.springboot.multiport.payload.SigninRequest;
import fi.ishtech.practice.springboot.multiport.payload.SignupRequest;
import fi.ishtech.practice.springboot.multiport.repo.UserRepo;
import fi.ishtech.practice.springboot.multiport.security.jwt.JwtUtil;
import fi.ishtech.practice.springboot.multiport.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserService userService;

	@PostMapping("/signin")
	public ResponseEntity<?> signin(@Valid @RequestBody SigninRequest signinRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return ResponseEntity.ok(jwtUtil.getJwtResponse(authentication));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
		log.debug("Signigup: {}", signupRequest.getUsername());

		if (userRepo.existsByEmail(signupRequest.getUsername())) {
			log.warn("Username: {} already exists", signupRequest.getUsername());
			throw new UsernameAlreadyExistsException();
		}

		User user = userService.create(signupRequest);

		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/users/{userId}")
				.buildAndExpand(user.getId()).toUri();

		return ResponseEntity.created(uri).body(user.getId());
	}

	@PreAuthorize(value = "hasAuthority('ROLE_USER')")
	@PatchMapping("/update-password")
	public ResponseEntity<Void> update(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long userId = jwtUtil.getUserId(authentication);
		log.debug("Updating password for {}", userId);

		userService.updatePassword(userId, passwordUpdateRequest);

		return ResponseEntity.ok(null);
	}

}
