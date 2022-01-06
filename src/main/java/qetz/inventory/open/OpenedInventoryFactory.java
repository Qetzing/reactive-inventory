package qetz.inventory.open;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import qetz.inventory.Inventory;

import java.util.UUID;

public final class OpenedInventoryFactory {
  private final KeepOpenedInventoryRepository keepOpenedInventoryRepository;
  private final OpenedInventoryRepository openedInventoryRepository;
  private final Injector injector;

  private Inventory inventory;
  private Plugin plugin;
  private UUID userId;

  @Inject
  private OpenedInventoryFactory(
    KeepOpenedInventoryRepository keepOpenedInventoryRepository,
    OpenedInventoryRepository openedInventoryRepository,
    Injector injector
  ) {
    this.keepOpenedInventoryRepository = keepOpenedInventoryRepository;
    this.openedInventoryRepository = openedInventoryRepository;
    this.injector = injector;
  }

  public OpenedInventoryFactory withUserId(UUID userId) {
    this.userId = userId;
    return this;
  }

  public OpenedInventoryFactory withInjectableInventory(
    Class<? extends Inventory> inventory
  ) {
    this.inventory = injector.getInstance(inventory);
    return this;
  }

  public OpenedInventoryFactory withInventory(Inventory inventory) {
    this.inventory = inventory;
    return this;
  }

  public OpenedInventoryFactory openSynchronized(Plugin plugin) {
    this.plugin = plugin;
    return this;
  }

  public OpenedInventory apply() {
    var openedInventory = createOpenedInventory();
    keepUpAndOpen(openedInventory);
    return openedInventory;
  }

  private void keepUpAndOpen(OpenedInventory openedInventory) {
    if (plugin == null) {
      openedInventoryRepository.keepUpInventory(userId, openedInventory);
      openedInventory.openInventory();
      return;
    }
    Bukkit.getScheduler().runTask(plugin, () -> {
      openedInventoryRepository.keepUpInventory(userId, openedInventory);
      openedInventory.openInventory();
    });
  }

  private OpenedInventory createOpenedInventory() {
    return OpenedInventory.newBuilder()
      .withKeepOpenedInventoryRepository(keepOpenedInventoryRepository)
      .withInventory(inventory)
      .withUserId(userId)
      .create();
  }
}