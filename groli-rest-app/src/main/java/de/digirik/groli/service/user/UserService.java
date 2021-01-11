package de.digirik.groli.service.user;

import java.util.List;

import de.digirik.groli.model.dto.request.RegistrationRequest;
import de.digirik.groli.model.entity.user.Role;
import de.digirik.groli.model.entity.user.User;
import de.digirik.groli.model.exception.UserAlreadyExistsException;

public interface UserService {

	List<User> getAllUsers();

	List<User> findAllByUsernameIn(List<String> usernames);

	User createUser(RegistrationRequest registrationRequest)
	        throws UserAlreadyExistsException;

	void deleteUser(String username);

	User addRole(String username, Role roleName);

}
