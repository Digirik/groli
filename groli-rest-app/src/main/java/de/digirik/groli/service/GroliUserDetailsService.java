package de.digirik.groli.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.digirik.groli.configuration.GroliUserPrincipal;
import de.digirik.groli.model.entity.user.User;
import de.digirik.groli.repository.user.UserRepository;

@Service
public class GroliUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public GroliUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new GroliUserPrincipal(user);
	}
}
