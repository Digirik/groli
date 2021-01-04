package de.digirik.groli.model.entity.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole {

	private long id;
	private String roleName;
	private List<User> users;

	public UserRole() {
	}

	public UserRole(String roleName) {
		this.roleName = roleName;
	}

	@Id
	@GeneratedValue(
	        strategy = GenerationType.SEQUENCE,
	        generator = "user_role_sequence_generator")
	@SequenceGenerator(
	        name = "user_role_sequence_generator",
	        sequenceName = "user_role_sequence",
	        allocationSize = 1)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(nullable = false, unique = true)
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String rolename) {
		this.roleName = rolename;
	}

	@ManyToMany
	@JoinTable(
	        name = "users_roles",
	        joinColumns = @JoinColumn(
	                name = "user_id",
	                referencedColumnName = "id"),
	        inverseJoinColumns = @JoinColumn(
	                name = "role_id",
	                referencedColumnName = "id"))
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
