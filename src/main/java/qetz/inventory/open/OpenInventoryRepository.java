package qetz.inventory.open;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import qetz.inventory.Inventory;
import qetz.inventory.actions.InventoryAction;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OpenInventoryRepository {
  public static OpenInventoryRepository empty() {
    return new OpenInventoryRepository(Maps.newHashMap());
  }

  private final Map<UUID, OpenInventory> inventories;

  void add(UUID userId, OpenInventory openInventory) {
    Preconditions.checkNotNull(userId, "userId");
    Preconditions.checkNotNull(openInventory, "openInventory");
    Preconditions.checkArgument(
      !inventories.containsKey(userId),
      "player already has an opened inventory"
    );
    inventories.put(userId, openInventory);
  }

  void remove(UUID userId) {
    Preconditions.checkNotNull(userId, "userId");
    inventories.remove(userId);
  }

  public void performActionOnId(InventoryAction action, UUID userId) {
    Preconditions.checkNotNull(action, "action");
    Preconditions.checkNotNull(userId, "userId");
    Optional.ofNullable(inventories.get(userId)).ifPresent(
      inventory -> performAction(inventory, action)
    );
  }

  private void performAction(OpenInventory inventory, InventoryAction action) {
    action.asExecutable()
      .withTarget(inventory)
      .perform();
  }

  public void performActionOnType(
    Class<? extends Inventory> type,
    InventoryAction action
  ) {
    Preconditions.checkNotNull(type, "type");
    Preconditions.checkNotNull(action, "action");
    inventories.values().stream()
      .filter(inventory -> inventory.isType(type))
      .forEach(inventory -> performAction(inventory, action));
  }
}