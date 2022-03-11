package qetz.inventory.open;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import qetz.inventory.Inventory;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public final class OpenedInventoryRepository {
  public static OpenedInventoryRepository create() {
    return new OpenedInventoryRepository(Maps.newHashMap());
  }

  private final Map<UUID, OpenedInventory> inventories;

  private OpenedInventoryRepository(Map<UUID, OpenedInventory> inventories) {
    this.inventories = inventories;
  }

  public void keepUpInventory(UUID userId, OpenedInventory openedInventory) {
    Preconditions.checkNotNull(userId, "userId");
    Preconditions.checkNotNull(openedInventory, "openedInventory");
    Preconditions.checkArgument(findOpenedInventory(userId).isEmpty(),
      "players has already an open inventory");
    inventories.put(userId, openedInventory);
  }

  public void removeOpenedInventory(UUID userId) {
    Preconditions.checkNotNull(userId, "userId");
    inventories.remove(userId);
  }

  public Optional<OpenedInventory> findOpenedInventory(UUID userId) {
    Preconditions.checkNotNull(userId, "userId");
    return Optional.ofNullable(inventories.get(userId));
  }

  public Collection<OpenedInventory> findWithSameType(
    Class<? extends Inventory> inventoryType
  ) {
    Preconditions.checkNotNull(inventoryType, "inventoryType");
    return inventories.values().stream()
      .filter(inventory -> inventory.matchesInventory(inventoryType))
      .collect(Collectors.toList());
  }

  public void triggerInventoryTypeUpdate(
    Class<? extends Inventory> inventoryType
  ) {
    Preconditions.checkNotNull(inventoryType, "inventoryType");
    inventories.values().stream()
      .filter(inventory -> inventory.matchesInventory(inventoryType))
      .findAny()
      .ifPresent(inventory -> inventory.triggerInventoryTypeUpdate(this));
  }

  public void closeInventoryByType(
    Class<? extends Inventory> inventoryType
  ) {
    Preconditions.checkNotNull(inventoryType, "inventoryType");
    inventories.values().stream()
      .filter(inventory -> inventory.matchesInventory(inventoryType))
      .findAny()
      .ifPresent(OpenedInventory::closeInventory);
  }
}