package fi.ishtech.practice.springboot.multiport.security.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import fi.ishtech.practice.springboot.multiport.payload.JwtResponse;
import fi.ishtech.practice.springboot.multiport.security.userdetails.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

	@Value("${fi.ishtech.practice.springboot.multiport.jwt.secret}")
	private String jwtSecret;

	@Value("${fi.ishtech.practice.springboot.multiport.jwt.experition-ms}")
	private Integer jwtExpirationMs;

	private static final String issuer = "codingexercise.ishtech.fi";

	private String generateJwtToken(UserDetailsImpl userDetails, Date iat, Date exp) {

		return Jwts.builder().setSubject((userDetails.getUsername())).setIssuedAt(iat).setExpiration(exp)
				.setIssuer(issuer).signWith(jwtKey(), SignatureAlgorithm.HS256).compact();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parserBuilder().setSigningKey(jwtKey()).build().parse(authToken);
			return true;
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	public String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}

	public String getUserNameFromJwtToken(String token) {
		return getClaimsBody(token).getSubject();
	}

	private Claims getClaimsBody(String token) {
		return Jwts.parserBuilder().setSigningKey(jwtKey()).build().parseClaimsJws(token).getBody();
	}

	private Key jwtKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	public JwtResponse getJwtResponse(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Date iat = new Date();
		Date exp = new Date(iat.getTime() + jwtExpirationMs);

		String jwt = generateJwtToken(userDetails, iat, exp);
		return JwtResponse.of(issuer, jwt, userDetails.getUsername(), userDetails.getScopes(), iat.getTime(),
				exp.getTime());
	}

	public UserDetailsImpl getUserDetails(Authentication authentication) {
		return (UserDetailsImpl) authentication.getPrincipal();
	}

	public Long getUserId(Authentication authentication) {
		return getUserDetails(authentication).getId();
	}

	public String getUsername(Authentication authentication) {
		return getUserDetails(authentication).getUsername();
	}

}