package qetz.inventory;

import qetz.inventory.open.OpenedInventoryRepository;

public interface ReactiveInventory extends Inventory {
  default void triggerUpdate(
    OpenedInventoryRepository openedInventoryRepository
  ) {
    var openInventories = openedInventoryRepository.findWithSameType(getClass());
    for (var openInventory : openInventories) {
      openInventory.reopenInventory();
    }
  }
}