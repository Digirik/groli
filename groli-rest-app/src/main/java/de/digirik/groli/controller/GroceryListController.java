package de.digirik.groli.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.digirik.groli.model.dto.request.IdRequest;
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
import de.digirik.groli.service.grocerylist.GroceryListService;

@RestController
@RequestMapping("/api/user/grocery-list")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class GroceryListController {

	private final GroceryListService groceryListService;

	public GroceryListController(GroceryListService groceryListService) {
		this.groceryListService = groceryListService;
	}

	@GetMapping("/own")
	public List<GroceryList> findAllOwnedGroceryLists() {
		return groceryListService.findAllOwnedGroceryLists();
	}

	@GetMapping("/invited")
	public List<GroceryList> findAllInvitedGroceryLists() {
		return groceryListService.findAllInvitedGroceryLists();
	}

	@GetMapping("/invitable")
	public List<String> findAllInvitableUsernames(
	        @RequestBody @Valid IdRequest idRequest)
	        throws NotYourGroceryListException,
	        GroceryListDoesNotExistException {
		return groceryListService.findAllInvitableUsernames(idRequest.getId());
	}

	@PostMapping("/create")
	public GroceryList createGroceryList(
	        @RequestBody @Valid CreateGroceryListRequest createGroceryListRequest) {
		return groceryListService.createGroceryList(createGroceryListRequest);
	}

	@PostMapping("/delete")
	public void deleteGroceryList(@RequestBody @Valid IdRequest idRequest)
	        throws NotYourGroceryListException,
	        GroceryListDoesNotExistException {
		groceryListService.deleteGroceryList(idRequest.getId());
	}

	@PostMapping("/edit/name")
	public GroceryList editGroceryList(
	        @RequestBody @Valid RenameGroceryListRequest renameGroceryListRequest)
	        throws NotYourGroceryListException,
	        GroceryListDoesNotExistException {
		return groceryListService.editGroceryList(renameGroceryListRequest);
	}

	@PostMapping("/invite")
	public GroceryList inviteToGroceryList(
	        @RequestBody @Valid InviteToGroceryListRequest inviteToGroceryListRequest)
	        throws NotYourGroceryListException,
	        GroceryListDoesNotExistException {
		return groceryListService
		    .inviteToGroceryList(inviteToGroceryListRequest);
	}

	@PostMapping("/add/item")
	public GroceryListItem addItemToGroceryList(
	        @RequestBody @Valid AddGroceryListItemRequest addGroceryListItemRequest)
	        throws NotYourGroceryListException,
	        GroceryListDoesNotExistException {
		return groceryListService
		    .addItemToGroceryList(addGroceryListItemRequest);
	}

	@PostMapping("/edit/item")
	public GroceryListItem editGroceryListItem(
	        @RequestBody @Valid EditGroceryListItemRequest editGroceryListItemRequest)
	        throws GroceryListDoesNotExistException,
	        GroceryListItemDoesNotExistException, NotYourGroceryListException {

		return groceryListService
		    .editGroceryListItem(editGroceryListItemRequest);
	}

	@PostMapping("/remove/item")
	public void removeGroceryListItem(@RequestBody @Valid IdRequest idRequest)
	        throws NotYourGroceryListException,
	        GroceryListDoesNotExistException,
	        GroceryListItemDoesNotExistException {
		groceryListService.removeGroceryListItem(idRequest.getId());
	}
}
