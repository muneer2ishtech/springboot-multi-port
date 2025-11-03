package fi.ishtech.practice.springboot.multiport.security.userdetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Muneer Ahmed Syed
 */
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 6774079004291687456L;

	@Getter
	@Setter(lombok.AccessLevel.PRIVATE)
	private Long id;

	@Getter
	@Setter(lombok.AccessLevel.PRIVATE)
	private String username;

	@Getter
	@Setter(lombok.AccessLevel.PRIVATE)
	@ToString.Exclude
	@JsonIgnore
	private String password;

	@Getter
	@Setter(lombok.AccessLevel.PRIVATE)
	private Collection<? extends GrantedAuthority> authorities;

	@Getter
	private boolean isAccountNonExpired = true;

	@Getter
	private boolean isAccountNonLocked = true;

	@Getter
	private boolean isCredentialsNonExpired = true;

	@Getter
	private boolean isEnabled = true;

	public static UserDetails of(Long id, String username, String password) {
		UserDetailsImpl userDetails = new UserDetailsImpl();

		userDetails.setId(id);
		userDetails.setUsername(username);
		userDetails.setPassword(password);

		userDetails.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

		return userDetails;
	}

	@JsonIgnore
	public List<String> getScopes() {
		return getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
	}

}
