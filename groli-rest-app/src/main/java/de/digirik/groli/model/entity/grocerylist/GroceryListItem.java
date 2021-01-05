package de.digirik.groli.model.entity.grocerylist;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

import de.digirik.groli.model.entity.user.User;

@Entity
public class GroceryListItem {

	private long id;
	private Date created;

	private GroceryList groceryList;
	private String description;
	private User addedBy;

	public GroceryListItem() {
		this.created = new Date();
	}

	@Id
	@GeneratedValue(
	        strategy = GenerationType.SEQUENCE,
	        generator = "grocery_list_item_sequence_generator")
	@SequenceGenerator(
	        name = "grocery_list_item_sequence_generator",
	        sequenceName = "grocery_list_item_sequence",
	        allocationSize = 1)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@ManyToOne
	@NotNull
	@JoinColumn(name = "grocery_list_id")
	public GroceryList getGroceryList() {
		return groceryList;
	}

	public void setGroceryList(GroceryList groceryList) {
		this.groceryList = groceryList;
	}

	@NotNull
	@Column(nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToOne
	@NotNull
	@JoinColumn(name = "user_id")
	public User getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
	}
}
