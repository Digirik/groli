package de.digirik.groli.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.digirik.groli.model.dto.request.CreateGroceryListRequest;
import de.digirik.groli.model.entity.grocerylist.GroceryList;
import de.digirik.groli.model.entity.grocerylist.GroceryListItem;
import de.digirik.groli.model.exception.GroceryListDoesNotExistException;
import de.digirik.groli.model.exception.GroceryListItemDoesNotExistException;
import de.digirik.groli.model.exception.NotYourGroceryListException;
import de.digirik.groli.service.grocerylist.GroceryListService;

@RestController
@RequestMapping("/api/user/grocery-list")
@PreAuthorize("hasRole('USER')")
public class GroceryListController {

	private final GroceryListService groceryListService;

	public GroceryListController(GroceryListService groceryListService) {
		this.groceryListService = groceryListService;
	}

	@GetMapping("/own")
	private List<GroceryList> findAllOwnedGroceryLists() {
		return groceryListService.findAllOwnedGroceryLists();
	}

	@GetMapping("/invited")
	private List<GroceryList> findAllInvitedGroceryLists() {
		return groceryListService.findAllInvitedGroceryLists();
	}

	@GetMapping("/invitable")
	private List<String> findAllInvitableUsernames(long groceryListId)
	        throws NotYourGroceryListException,
	        GroceryListDoesNotExistException {
		return groceryListService.findAllInvitableUsernames(groceryListId);
	}

	@PostMapping("/create")
	public GroceryList createGroceryList(
	        @RequestBody CreateGroceryListRequest createGroceryListRequest) {
		return groceryListService.createGroceryList(createGroceryListRequest);
	}

	@PostMapping("/delete")
	public void deleteGroceryList(long groceryListId)
	        throws NotYourGroceryListException,
	        GroceryListDoesNotExistException {
		groceryListService.deleteGroceryList(groceryListId);
	}

	@PostMapping("/edit/name")
	public GroceryList editGroceryList(long groceryListId,
	        String groceryListName) throws NotYourGroceryListException,
	        GroceryListDoesNotExistException {
		return groceryListService.editGroceryList(groceryListId,
		    groceryListName);
	}

	@PostMapping("/invite")
	public GroceryList inviteToGroceryList(long groceryListId,
	        List<String> usernames) throws NotYourGroceryListException,
	        GroceryListDoesNotExistException {
		return groceryListService.inviteToGroceryList(groceryListId, usernames);
	}

	@PostMapping("/add/item")
	private GroceryListItem addItemToGroceryList(long groceryListId,
	        String description) throws NotYourGroceryListException,
	        GroceryListDoesNotExistException {
		return groceryListService.addItemToGroceryList(groceryListId,
		    description);
	}

	@PostMapping("/edit/item")
	private GroceryListItem editGroceryListItem(long groceryListId,
	        long groceryListItemId, String description)
	        throws GroceryListDoesNotExistException,
	        GroceryListItemDoesNotExistException, NotYourGroceryListException {

		return groceryListService.editGroceryListItem(groceryListId,
		    groceryListItemId, description);
	}

	@PostMapping("/remove/item")
	private void removeGroceryListItem(long groceryListId,
	        long groceryListItemId) {

	}
}
