package de.digirik.groli.service.grocerylist.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import de.digirik.groli.model.dto.request.CreateGroceryListRequest;
import de.digirik.groli.model.entity.grocerylist.GroceryList;
import de.digirik.groli.model.entity.grocerylist.GroceryListItem;
import de.digirik.groli.model.entity.user.User;
import de.digirik.groli.model.exception.GroceryListDoesNotExistException;
import de.digirik.groli.model.exception.GroceryListItemDoesNotExistException;
import de.digirik.groli.model.exception.NotYourGroceryListException;
import de.digirik.groli.repository.grocerylist.GroceryListItemRepository;
import de.digirik.groli.repository.grocerylist.GroceryListRepository;
import de.digirik.groli.service.auth.AuthService;
import de.digirik.groli.service.grocerylist.GroceryListService;
import de.digirik.groli.service.user.UserService;

@Service
public class GroceryListServiceImpl implements GroceryListService {

	private final AuthService authService;

	private final UserService userService;

	private final GroceryListRepository groceryListRepository;
	private final GroceryListItemRepository groceryListItemRepository;

	public GroceryListServiceImpl(AuthService authService,
	        UserService userService,
	        GroceryListRepository groceryListRepository,
	        GroceryListItemRepository groceryListItemRepository) {
		this.authService = authService;
		this.userService = userService;
		this.groceryListRepository = groceryListRepository;
		this.groceryListItemRepository = groceryListItemRepository;
	}

	@Override
	public List<GroceryList> findAllOwnedGroceryLists() {

		User owner = authService.getAuthenticatedUser();

		return groceryListRepository.findAllByOwner(owner);
	}

	@Override
	public List<GroceryList> findAllInvitedGroceryLists() {

		User authenticatedUser = authService.getAuthenticatedUser();

		return groceryListRepository
		    .findAllByInvitedUsersContains(authenticatedUser);
	}

	@Override
	public List<String> findAllInvitableUsernames(long groceryListId)
	        throws GroceryListDoesNotExistException,
	        NotYourGroceryListException {

		GroceryList groceryList = groceryListRepository.getById(groceryListId);

		User requestingUser = authService.getAuthenticatedUser();
		if (groceryList.userIsNotOwner(requestingUser)) {
			throw new NotYourGroceryListException(
			    "Your are not the owner to the grocery list with id "
			            + groceryListId
			            + ". Only the owner can find invitable users.");
		}

		return userService.getAllUsers()
		    .stream()
		    .filter(user -> !groceryList.getInvitedUsers().contains(user))
		    .map(User::getUsername)
		    .collect(Collectors.toList());
	}

	@Override
	@Transactional
	public GroceryList createGroceryList(
	        CreateGroceryListRequest createGroceryListRequest) {

		User requestingUser = authService.getAuthenticatedUser();
		List<User> invitedUsers = userService
		    .findAllByUsernameIn(createGroceryListRequest.getInvitedUsers());
		GroceryList groceryList = new GroceryList(
		    createGroceryListRequest.getName(), requestingUser, invitedUsers);

		return groceryListRepository.save(groceryList);
	}

	// todo: use deleteById?
	@Override
	@Transactional
	public void deleteGroceryList(long groceryListId)
	        throws GroceryListDoesNotExistException,
	        NotYourGroceryListException {

		GroceryList groceryList = groceryListRepository.getById(groceryListId);

		User requestingUser = authService.getAuthenticatedUser();
		if (groceryList.userIsNotOwner(requestingUser)) {
			throw new NotYourGroceryListException(
			    "Your are not the owner to the grocery list with id "
			            + groceryListId + ". Only the owner can delete it.");
		}

		groceryListRepository.delete(groceryList);
	}

	// TODO: rename this and controller method to editGroceryListName?
	@Override
	@Transactional
	public GroceryList editGroceryList(long groceryListId,
	        String groceryListName) throws GroceryListDoesNotExistException,
	        NotYourGroceryListException {

		GroceryList groceryList = groceryListRepository.getById(groceryListId);

		User requestingUser = authService.getAuthenticatedUser();
		if (groceryList.userIsNotOwner(requestingUser)) {
			throw new NotYourGroceryListException(
			    "Your are not the owner to the grocery list with id "
			            + groceryListId
			            + ". Only the owner can change its name.");
		}

		groceryList.setName(groceryListName);

		return groceryListRepository.save(groceryList);
	}

	@Override
	@Transactional
	public GroceryList inviteToGroceryList(long groceryListId,
	        List<String> usernames) throws GroceryListDoesNotExistException,
	        NotYourGroceryListException {

		GroceryList groceryList = groceryListRepository.getById(groceryListId);

		User requestingUser = authService.getAuthenticatedUser();
		if (groceryList.userIsNotOwner(requestingUser)) {
			throw new NotYourGroceryListException(
			    "Your are not the owner to the grocery list with id "
			            + groceryListId + ". Only the owner can invite users.");
		}

		List<User> newlyInvitedUsers =
		        userService.findAllByUsernameIn(usernames);
		newlyInvitedUsers.forEach(groceryList::addInvitedUser);

		return groceryListRepository.save(groceryList);
	}

	@Override
	@Transactional
	public GroceryListItem addItemToGroceryList(long groceryListId,
	        String description) throws GroceryListDoesNotExistException,
	        NotYourGroceryListException {

		GroceryList groceryList = groceryListRepository.getById(groceryListId);

		User requestingUser = authService.getAuthenticatedUser();
		if (groceryList.userMayNotEdit(requestingUser)) {
			throw new NotYourGroceryListException(
			    "Your are neither the owner of nor invited to the grocery list with id "
			            + groceryListId + ", don't try editing it.");
		}

		GroceryListItem groceryListItem =
		        new GroceryListItem(requestingUser, description);
		groceryListItem.setGroceryList(groceryList);

		return groceryListItemRepository.save(groceryListItem);
	}

	// todo: check if item belongs to grocery list?
	@Override
	@Transactional
	public GroceryListItem editGroceryListItem(long groceryListId,
	        long groceryListItemId, String description)
	        throws GroceryListDoesNotExistException,
	        NotYourGroceryListException, GroceryListItemDoesNotExistException {

		GroceryList groceryList = groceryListRepository.getById(groceryListId);

		User requestingUser = authService.getAuthenticatedUser();
		if (groceryList.userMayNotEdit(requestingUser)) {
			throw new NotYourGroceryListException(
			    "Your are neither the owner of nor invited to the grocery list with id "
			            + groceryListId + ", don't try editing it.");
		}

		GroceryListItem groceryListItem =
		        groceryListItemRepository.getById(groceryListItemId);
		groceryListItem.setDescription(description);

		return groceryListItemRepository.save(groceryListItem);
	}

	@Override
	public void removeGroceryListItem(long groceryListId,
	        long groceryListItemId) throws GroceryListDoesNotExistException,
	        NotYourGroceryListException {

		GroceryList groceryList = groceryListRepository.getById(groceryListId);

		User requestingUser = authService.getAuthenticatedUser();
		if (groceryList.userMayNotEdit(requestingUser)) {
			throw new NotYourGroceryListException(
			    "Your are neither the owner of nor invited to the grocery list with id "
			            + groceryListId + ", don't try deleting items.");
		}
	}
}
