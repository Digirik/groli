package de.digirik.groli.service.grocerylist;

import java.util.List;

import de.digirik.groli.model.dto.request.CreateGroceryListRequest;
import de.digirik.groli.model.entity.grocerylist.GroceryList;
import de.digirik.groli.model.entity.grocerylist.GroceryListItem;
import de.digirik.groli.model.exception.GroceryListDoesNotExistException;
import de.digirik.groli.model.exception.GroceryListItemDoesNotExistException;
import de.digirik.groli.model.exception.NotYourGroceryListException;

public interface GroceryListService {

	List<GroceryList> findAllOwnedGroceryLists();

	List<GroceryList> findAllInvitedGroceryLists();

	List<String> findAllInvitableUsernames(long groceryListId)
	        throws GroceryListDoesNotExistException,
	        NotYourGroceryListException;

	GroceryList createGroceryList(
	        CreateGroceryListRequest createGroceryListRequest);

	void deleteGroceryList(long groceryListId)
	        throws GroceryListDoesNotExistException,
	        NotYourGroceryListException;

	GroceryList editGroceryList(long groceryListId, String groceryListName)
	        throws GroceryListDoesNotExistException,
	        NotYourGroceryListException;

	GroceryList inviteToGroceryList(long groceryListId, List<String> usernames)
	        throws GroceryListDoesNotExistException,
	        NotYourGroceryListException;

	GroceryListItem addItemToGroceryList(long groceryListId, String description)
	        throws GroceryListDoesNotExistException,
	        NotYourGroceryListException;

	GroceryListItem editGroceryListItem(long groceryListId,
	        long groceryListItemId, String description)
	        throws GroceryListDoesNotExistException,
	        NotYourGroceryListException, GroceryListItemDoesNotExistException;

	void removeGroceryListItem(long groceryListId, long groceryListItemId)
	        throws GroceryListDoesNotExistException,
	        NotYourGroceryListException;
}
