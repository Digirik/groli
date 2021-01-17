package de.digirik.groli.service.grocerylist;

import java.util.List;

import de.digirik.groli.model.dto.request.grocerylist.AddGroceryListItemRequest;
import de.digirik.groli.model.dto.request.grocerylist.CreateGroceryListRequest;
import de.digirik.groli.model.dto.request.grocerylist.EditGroceryListItemRequest;
import de.digirik.groli.model.dto.request.grocerylist.InviteToGroceryListRequest;
import de.digirik.groli.model.dto.request.grocerylist.RenameGroceryListRequest;
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

	GroceryList editGroceryList(
	        RenameGroceryListRequest renameGroceryListRequest)
	        throws GroceryListDoesNotExistException,
	        NotYourGroceryListException;

	GroceryList inviteToGroceryList(
	        InviteToGroceryListRequest inviteToGroceryListRequest)
	        throws GroceryListDoesNotExistException,
	        NotYourGroceryListException;

	GroceryListItem addItemToGroceryList(
	        AddGroceryListItemRequest addGroceryListItemRequest)
	        throws GroceryListDoesNotExistException,
	        NotYourGroceryListException;

	GroceryListItem editGroceryListItem(
	        EditGroceryListItemRequest editGroceryListItemRequest)
	        throws GroceryListDoesNotExistException,
	        NotYourGroceryListException, GroceryListItemDoesNotExistException;

	void removeGroceryListItem(long groceryListItemId)
	        throws GroceryListDoesNotExistException,
	        NotYourGroceryListException, GroceryListItemDoesNotExistException;
}
