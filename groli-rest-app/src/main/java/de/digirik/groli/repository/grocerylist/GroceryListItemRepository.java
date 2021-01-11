package de.digirik.groli.repository.grocerylist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.digirik.groli.model.entity.grocerylist.GroceryListItem;
import de.digirik.groli.model.exception.GroceryListItemDoesNotExistException;

@Repository
public interface GroceryListItemRepository
        extends JpaRepository<GroceryListItem, Long> {

	default GroceryListItem getById(long id)
	        throws GroceryListItemDoesNotExistException {
		return findById(id)
		    .orElseThrow(() -> new GroceryListItemDoesNotExistException(
		        "Could not find grocery list item with id " + id));
	}
}
