package de.digirik.groli.model.entity.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {

	private long id;
	private String username;
	private String password;
	private boolean active = true;
	private List<UserRole> roles;

	public User() {
	}

	public User(String username, String password, List<UserRole> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Id
	@GeneratedValue(
	        strategy = GenerationType.SEQUENCE,
	        generator = "users_sequence_generator")
	@SequenceGenerator(
	        name = "users_sequence_generator",
	        sequenceName = "users_sequence",
	        allocationSize = 1)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(nullable = false, unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	        name = "users_roles",
	        joinColumns = @JoinColumn(
	                name = "user_id",
	                referencedColumnName = "id"),
	        inverseJoinColumns = @JoinColumn(
	                name = "role_id",
	                referencedColumnName = "id"))
	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	@Transient
	public void addRole(UserRole userRole) {
		Set<UserRole> userRolesSet = new HashSet<>(this.roles);
		userRolesSet.add(userRole);
		List<UserRole> userRoles = new ArrayList<>(userRolesSet);
		setRoles(userRoles);
	}

	@Transient
	public void removeRole(UserRole userRole) {
		this.roles.remove(userRole);
	}
}
