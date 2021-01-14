package de.digirik.groli.configuration;

import static java.util.Objects.isNull;

import org.springframework.beans.factory.annotation.Value;
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

	private final String adminUsername;
	private final String adminPasswordHash;

	public AdminInit(UserRepository userRepository,
	        UserRoleRepository userRoleRepository,
	        PasswordEncoder passwordEncoder,
	        @Value("${groli.security.admin.init-username}") String adminUsername,
	        @Value("${groli.security.admin.init-pw-hash}") String adminPasswordHash) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
		this.adminUsername = adminUsername;
		this.adminPasswordHash = adminPasswordHash;
	}

	// todo: add pw reset function and require pw change on first login
	@Override
	public void run(String... args) {

		User adminUser = userRepository.findByUsername("admin");
		if (isNull(adminUser)) {
			User savedAdminUser = userRepository
			    .save(new User(adminUsername, adminPasswordHash));

			UserRole adminAdmin = new UserRole(savedAdminUser, Role.ADMIN);
			userRoleRepository.save(adminAdmin);
		}
	}
}
