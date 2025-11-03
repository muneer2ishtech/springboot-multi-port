package fi.ishtech.practice.springboot.multiport.payload;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
public class PasswordUpdateRequest implements Serializable {

	private static final long serialVersionUID = -2440397596568638277L;

	@NotBlank
	@ToString.Exclude
	private String oldPassword;

	@NotBlank
	@ToString.Exclude
	private String newPassword;

	@NotBlank
	@ToString.Exclude
	private String newPasswordRepeat;

	@AssertTrue(message = "newPassword and newPasswordRepeat are not matching")
	@JsonIgnore
	public boolean isNewPasswordAndNewPasswordRepeatMatch() {
		return newPassword.equals(newPasswordRepeat);
	}

	@AssertTrue(message = "newPassword cannot be same as oldPassword")
	@JsonIgnore
	public boolean isNewPasswordAndOldPasswordMatch() {
		return !newPassword.equals(oldPassword);
	}

}
