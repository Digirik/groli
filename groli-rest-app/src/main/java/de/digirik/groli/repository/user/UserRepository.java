package de.digirik.groli.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.digirik.groli.model.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	// TODO: use optional?
	User findByUsername(String username);

	List<User> findAllByUsernameIn(List<String> username);

	void deleteByUsername(String username);
}
