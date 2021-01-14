package de.digirik.groli.model.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "user_role")
public class UserRole {

	private long id;
	private Role roleName;
	private User user;

	public UserRole() {
	}

	public UserRole(User user, Role roleName) {
		this.user = user;
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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	public Role getRoleName() {
		return roleName;
	}

	public void setRoleName(Role roleName) {
		this.roleName = roleName;
	}

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
