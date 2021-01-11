package de.digirik.groli.configuration;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import de.digirik.groli.model.entity.user.User;
import de.digirik.groli.model.entity.user.UserRole;

public class GroliUserPrincipal implements UserDetails {

	private User user;
	private String username;
	private String password;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private List<UserRole> authorities;

	public GroliUserPrincipal(User user) {
		this.user = user;
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authorities = user.getRoles();
		boolean active = user.isActive();
		this.accountNonExpired = active;
		this.credentialsNonExpired = active;
		this.accountNonLocked = active;
		this.enabled = active;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities.stream()
		    .map(userRole -> new SimpleGrantedAuthority(
		        "ROLE_" + userRole.getRoleName()))
		    .collect(Collectors.toList());
	}

	public void setAuthorities(List<UserRole> authorities) {
		this.authorities = authorities;
	}
}
