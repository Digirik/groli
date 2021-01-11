package de.digirik.groli.configuration;

import static java.util.Objects.isNull;

import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import de.digirik.groli.model.entity.user.Role;
import de.digirik.groli.model.entity.user.User;
import de.digirik.groli.model.entity.user.UserRole;
import de.digirik.groli.repository.user.UserRepository;
import de.digirik.groli.repository.user.UserRoleRepository;

@Component
public class AdminInit implements CommandLineRunner {

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final PasswordEncoder passwordEncoder;

	public AdminInit(UserRepository userRepository,
	        UserRoleRepository userRoleRepository,
	        PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {

		int numberOfUsers = userRepository.findAll().size();

		UserRole adminRole = userRoleRepository.findByRoleName(Role.ADMIN);
		if (isNull(adminRole)) {
			adminRole = userRoleRepository.save(new UserRole(Role.ADMIN));
		}

		User adminUser = userRepository.findByUsername("admin");
		if (isNull(adminUser) && numberOfUsers == 0) {
			userRepository
			    .save(new User("admin", passwordEncoder.encode("admin"),
			        Collections.singletonList(adminRole)));
		}
	}
}
