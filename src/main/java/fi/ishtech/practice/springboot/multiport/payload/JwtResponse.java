package fi.ishtech.practice.springboot.multiport.payload;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8822501923765318106L;

	@JsonProperty("token_type")
	@Builder.Default
	private String tokenType = "Bearer";

	private String iss;

	@JsonProperty("access_token")
	private String accessToken;

	private String sub;

	private Long iat;

	private Long exp;

	private List<String> scopes;

	public static JwtResponse of(String iss, String accessToken, String sub, List<String> scopes, Long iat, Long exp) {
		return JwtResponse.builder().iss(iss).accessToken(accessToken).sub(sub).scopes(scopes).iat(iat).exp(exp)
				.build();
	}

}
