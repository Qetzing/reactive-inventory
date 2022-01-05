package qetz.inventory;


import qetz.inventory.open.OpenedInventoryRepository;
import qetz.inventory.policy.InventoryPolicy;

import java.util.Set;

public abstract class ReactiveBukkitInventory extends BukkitInventory {
  private final OpenedInventoryRepository openedInventoryRepository;

  public ReactiveBukkitInventory(
    OpenedInventoryRepository openedInventoryRepository,
    Set<InventoryPolicy> inventoryPolicies,
    String id
  ) {
    super(inventoryPolicies, id);
    this.openedInventoryRepository = openedInventoryRepository;
  }

  public void triggerUpdate() {
    for (var openedInventory : openedInventoryRepository
      .findWithSameType(getClass()))
    {
      openedInventory.reopenInventory();
    }
  }
}