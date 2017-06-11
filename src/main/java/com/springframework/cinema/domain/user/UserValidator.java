package com.springframework.cinema.domain.user;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
	
	private static final int PASSWORD_MIN_LENGTH = 6; 
	private static final int USERNAME_MIN_LENGTH = 3;
	
	private final UserService userService;
	
	public UserValidator(UserRepository userRepository) {
		userService = new UserService(userRepository);
	}
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz) || CustomUserDetails.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty");
		User user = (User) object;
		String password = user.getPassword();
		if(password.length() < PASSWORD_MIN_LENGTH)
			errors.rejectValue("password", "password.too_short");
		//TODO regex comparision
		String username = user.getUsername();
		if(userService.checkIfUsernameExists(username))
			errors.rejectValue("username", "username.already_exists");
		if(username.length() < USERNAME_MIN_LENGTH)
			errors.rejectValue("username", "username.too_short");
		//TODO regex comparision
		String email = user.getEmail();
		if(userService.checkIfEmailExists(email))
			errors.rejectValue("email", "email.already_exists");
	}
}
