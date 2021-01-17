package de.digirik.groli.model.entity.grocerylist;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sun.istack.NotNull;

import de.digirik.groli.model.entity.user.User;

//TODO: remove fetch type eager from entities and write own repository that can init fields or ignore invitedUsers field on serialisation and add controller to get already invited users
@Entity
public class GroceryList {

	private long id;
	private Date created;

	private String name;
	private User owner;
	private List<User> invitedUsers;
	private List<GroceryListItem> groceryListItems;

	public GroceryList() {
		this.created = new Date();
	}

	public GroceryList(String name, User owner) {
		this.created = new Date();
		this.name = name;
		this.owner = owner;
	}

	public GroceryList(String name, User owner, List<User> invitedUsers) {
		this.created = new Date();
		this.name = name;
		this.owner = owner;
		this.invitedUsers = invitedUsers;
	}

	@Id
	@GeneratedValue(
	        strategy = GenerationType.SEQUENCE,
	        generator = "grocery_list_sequence_generator")
	@SequenceGenerator(
	        name = "grocery_list_sequence_generator",
	        sequenceName = "grocery_list_sequence",
	        allocationSize = 1)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Column(nullable = false)
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToOne
	@JoinColumn(name = "user_id")
	@NotNull
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@ManyToMany
	@JoinTable(
	        name = "grocery_list_invited_users",
	        joinColumns = @JoinColumn(
	                name = "grocery_list_id",
	                referencedColumnName = "id"),
	        inverseJoinColumns = @JoinColumn(
	                name = "user_id",
	                referencedColumnName = "id"))
	public List<User> getInvitedUsers() {
		return invitedUsers;
	}

	public void setInvitedUsers(List<User> invitedUsers) {
		this.invitedUsers = invitedUsers;
	}

	@Transient
	public void addInvitedUser(User user) {
		Set<User> invitedUsersSet = new HashSet<>(this.invitedUsers);
		invitedUsersSet.add(user);
		List<User> invitedUsers = new ArrayList<>(invitedUsersSet);
		setInvitedUsers(invitedUsers);
	}

	@OneToMany(mappedBy = "groceryList", fetch = FetchType.EAGER)
	public List<GroceryListItem> getGroceryListItems() {
		return groceryListItems;
	}

	public void setGroceryListItems(List<GroceryListItem> groceryListItems) {
		this.groceryListItems = groceryListItems;
	}

	@Transient
	public boolean userIsOwner(User user) {
		return this.owner.equals(user);
	}

	@Transient
	public boolean userIsNotOwner(User user) {
		return !userIsOwner(user);
	}

	@Transient
	public boolean userMayEdit(User user) {
		return (this.owner.equals(user) || this.invitedUsers.stream()
		    .anyMatch(invitedUser -> invitedUser.equals(user)));
	}

	@Transient
	public boolean userMayNotEdit(User user) {
		return !userMayEdit(user);
	}
}
