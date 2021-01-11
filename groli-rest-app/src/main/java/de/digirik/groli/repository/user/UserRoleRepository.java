package de.digirik.groli.repository.user;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.digirik.groli.model.entity.user.Role;
import de.digirik.groli.model.entity.user.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

	UserRole findByRoleName(Role roleName);

	List<UserRole> findALlByRoleNameIn(Collection<Role> roleName);

	void deleteByRoleName(Role roleName);
}
