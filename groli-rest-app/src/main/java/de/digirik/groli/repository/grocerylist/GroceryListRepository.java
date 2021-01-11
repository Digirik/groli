package de.digirik.groli.repository.grocerylist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.digirik.groli.model.entity.grocerylist.GroceryList;
import de.digirik.groli.model.entity.user.User;
import de.digirik.groli.model.exception.GroceryListDoesNotExistException;

@Repository
public interface GroceryListRepository
        extends JpaRepository<GroceryList, Long> {

	default GroceryList getById(long id)
	        throws GroceryListDoesNotExistException {
		return findById(id)
		    .orElseThrow(() -> new GroceryListDoesNotExistException(
		        "Could not find grocery list with id " + id));
	}

	List<GroceryList> findAllByOwner(User owner);

	List<GroceryList> findAllByInvitedUsersContains(User user);
}
