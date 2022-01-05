package qetz.inventory.open;

import com.google.inject.Inject;
import qetz.inventory.Inventory;

import java.util.UUID;

public final class OpenedInventoryFactory {
  private final KeepOpenedInventoryRepository keepOpenedInventoryRepository;
  private final OpenedInventoryRepository openedInventoryRepository;

  private Inventory inventory;
  private UUID userId;

  @Inject
  private OpenedInventoryFactory(
    KeepOpenedInventoryRepository keepOpenedInventoryRepository,
    OpenedInventoryRepository openedInventoryRepository
  ) {
    this.keepOpenedInventoryRepository = keepOpenedInventoryRepository;
    this.openedInventoryRepository = openedInventoryRepository;
  }

  public OpenedInventoryFactory withUserId(UUID userId) {
    this.userId = userId;
    return this;
  }

  public OpenedInventoryFactory withInventory(Inventory inventory) {
    this.inventory = inventory;
    return this;
  }

  public OpenedInventory apply() {
    var openedInventory = createOpenedInventory();
    keepUpAndOpen(openedInventory);
    return openedInventory;
  }

  private void keepUpAndOpen(OpenedInventory openedInventory) {
    openedInventoryRepository.keepUpInventory(userId, openedInventory);
    openedInventory.openInventory();
  }

  private OpenedInventory createOpenedInventory() {
    return OpenedInventory.newBuilder()
      .withKeepOpenedInventoryRepository(keepOpenedInventoryRepository)
      .withInventory(inventory)
      .withUserId(userId)
      .create();
  }
}