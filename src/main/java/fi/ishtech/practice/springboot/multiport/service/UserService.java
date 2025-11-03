package fi.ishtech.practice.springboot.multiport.service;

import fi.ishtech.practice.springboot.multiport.entity.User;
import fi.ishtech.practice.springboot.multiport.payload.PasswordUpdateRequest;
import fi.ishtech.practice.springboot.multiport.payload.SignupRequest;

/**
 *
 * @author Muneer Ahmed Syed
 */
public interface UserService {

	User findById(Long id);

	User create(SignupRequest signupRequest);

	User update(User user);

	void updatePassword(Long userId, PasswordUpdateRequest passwordUpdateRequest);

}
