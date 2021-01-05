package de.digirik.groli.model.entity.grocerylist;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

import de.digirik.groli.model.entity.user.User;

@Entity
public class GroceryList {

	private long id;
	private Date created;

	private User owner;
	private List<GroceryListItem> groceryListItems;

	public GroceryList() {
		this.created = new Date();
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

	@OneToOne
	@JoinColumn(name = "user_id")
	@NotNull
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "groceryList")
	public List<GroceryListItem> getGroceryListItems() {
		return groceryListItems;
	}

	public void setGroceryListItems(List<GroceryListItem> groceryListItems) {
		this.groceryListItems = groceryListItems;
	}
}
