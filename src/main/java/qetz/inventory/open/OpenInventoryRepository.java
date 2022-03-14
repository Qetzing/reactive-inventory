package qetz.inventory.open;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import qetz.inventory.Inventory;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
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
      findById(userId).isEmpty(),
      "player already has an opened inventory"
    );
    inventories.put(userId, openInventory);
  }

  void remove(UUID userId) {
    Preconditions.checkNotNull(userId, "userId");
    inventories.remove(userId);
  }

  public Optional<OpenInventory> findById(UUID userId) {
    Preconditions.checkNotNull(userId, "userId");
    return Optional.ofNullable(inventories.get(userId));
  }

  public Collection<OpenInventory> findByType(Class<? extends Inventory> type) {
    Preconditions.checkNotNull(type, "type");
    return inventories.values().stream()
      .filter(inventory -> inventory.isType(type))
      .collect(Collectors.toList());
  }
}