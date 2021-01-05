package de.digirik.groli.repository.grocerylist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.digirik.groli.model.entity.grocerylist.GroceryListItem;

@Repository
public interface GroceryListItemRepository
        extends JpaRepository<GroceryListItem, Long> {
}
