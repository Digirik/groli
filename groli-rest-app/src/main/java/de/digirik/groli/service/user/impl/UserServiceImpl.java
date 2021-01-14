package de.digirik.groli.service.user.impl;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.digirik.groli.model.dto.request.RegistrationRequest;
import de.digirik.groli.model.entity.user.Role;
import de.digirik.groli.model.entity.user.User;
import de.digirik.groli.model.entity.user.UserRole;
import de.digirik.groli.model.exception.UserAlreadyExistsException;
import de.digirik.groli.repository.user.UserRepository;
import de.digirik.groli.repository.user.UserRoleRepository;
import de.digirik.groli.service.user.UserService;
import javassist.NotFoundException;

//TODO: create own repo so we can use refresh()
@Service
public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;

	public UserServiceImpl(PasswordEncoder passwordEncoder,
	        UserRepository userRepository,
	        UserRoleRepository userRoleRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public List<User> findAllByUsernameIn(List<String> usernames) {
		return userRepository.findAllByUsernameIn(usernames);
	}

	@Override
	@Transactional
	public User createUser(RegistrationRequest registrationRequest)
	        throws UserAlreadyExistsException {
		String username = registrationRequest.getUsername();
		String password =
		        passwordEncoder.encode(registrationRequest.getPassword());

		User user = userRepository.findByUsername(username);
		if (nonNull(user)) {
			throw new UserAlreadyExistsException(
			    "Username already in use. Please choose a different username");
		}

		User savedUser = userRepository.save(new User(username, password));

		List<UserRole> userRoles = registrationRequest.getRoleNames()
		    .stream()
		    .map(Role::valueOf)
		    .map(role -> new UserRole(savedUser, role))
		    .collect(Collectors.toList());

		savedUser.setRoles(userRoleRepository.saveAll(userRoles));

		return savedUser;
	}

	@Override
	@Transactional
	public void deleteUser(String username) {
		userRepository.deleteByUsername(username);
	}

	@Override
	@Transactional
	public User addRole(String username, Role roleName)
	        throws NotFoundException {

		User user = userRepository.findByUsername(username);
		if (isNull(user)) {
			throw new NotFoundException(
			    "User with username " + username + "does not exist.");
		}

		UserRole userRole =
		        userRoleRepository.findByUserAndRoleName(user, roleName);
		if (nonNull(userRole)) {
			return user;
		}

		user.addRole(userRoleRepository.save(new UserRole(user, roleName)));

		return user;
	}

}
