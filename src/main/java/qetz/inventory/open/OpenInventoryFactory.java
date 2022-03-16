package qetz.inventory.open;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import qetz.inventory.Inventory;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_= @Inject)
public final class OpenInventoryFactory {
  private final OpenInventoryRepository openedInventories;
  private final Injector injector;

  private Inventory inventory;
  private UUID userId;

  public OpenInventoryFactory withUserId(UUID userId) {
    this.userId = userId;
    return this;
  }

  public OpenInventoryFactory withInventory(Inventory inventory) {
    this.inventory = inventory;
    return this;
  }

  public OpenInventoryFactory withInjectableInventory(
    Class<? extends Inventory> inventory
  ) {
    this.inventory = injector.getInstance(inventory);
    return this;
  }

  public void openSynchronized(Plugin plugin) {
    Preconditions.checkNotNull(plugin, "plugin");
    Bukkit.getScheduler().runTask(plugin, this::open);
  }

  public void open() {
    Preconditions.checkNotNull(inventory, "inventory");
    Preconditions.checkNotNull(userId, "userId");
    var opened = OpenInventory.with(inventory, userId);
    openedInventories.add(userId, opened);
    opened.open();
  }
}