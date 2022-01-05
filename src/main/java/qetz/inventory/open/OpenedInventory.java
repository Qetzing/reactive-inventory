package qetz.inventory.open;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import qetz.inventory.BukkitInventory;
import qetz.inventory.Inventory;
import qetz.inventory.ReactiveBukkitInventory;

import java.util.UUID;

public final class OpenedInventory {
  private final KeepOpenedInventoryRepository keepOpenedInventoryRepository;
  private final UUID userId;

  private Inventory inventory;

  private OpenedInventory(
    KeepOpenedInventoryRepository keepOpenedInventoryRepository,
    Inventory inventory,
    UUID userId
  ) {
    this.keepOpenedInventoryRepository = keepOpenedInventoryRepository;
    this.inventory = inventory;
    this.userId = userId;
  }

  public void updateInventory(Inventory newInventory) {
    Preconditions.checkNotNull(newInventory, "newInventory");
    this.inventory = newInventory;
    reopenInventory();
  }

  public void openInventory() {
    var target = Bukkit.getPlayer(userId);
    if (target != null) {
      inventory.open(target);
    }
  }

  public void reopenInventory() {
    keepOpenedInventoryRepository.add(userId);
    openInventory();
  }

  public boolean matchesInventory(
    Class<? extends Inventory> comparableInventory
  ) {
    Preconditions.checkNotNull(comparableInventory, "comparableInventory");
    return inventory.getClass().isAssignableFrom(comparableInventory);
  }

  public void triggerInteract(InventoryClickEvent click) {
    Preconditions.checkNotNull(click, "click");
    inventory.atInteract(click, (Player) click.getWhoClicked(),
      click.getWhoClicked().getUniqueId());
  }

  public boolean continueAndApplyPolicies(InventoryClickEvent click) {
    Preconditions.checkNotNull(click, "click");
    if (inventory instanceof BukkitInventory policyApplicableInventory
      && policyApplicableInventory.continueWithEvent(click)
    ) {
      policyApplicableInventory.applyPolicies(click);
      return true;
    }
    return false;
  }

  public void triggerInventoryTypeUpdate() {
    if (inventory instanceof ReactiveBukkitInventory reactiveInventory) {
      reactiveInventory.triggerUpdate();
    }
  }

  static Builder newBuilder() {
    return new Builder();
  }

  static final class Builder {
    private KeepOpenedInventoryRepository keepOpenedInventoryRepository;
    private Inventory inventory;
    private UUID userId;

    private Builder() {
    }

    public Builder withKeepOpenedInventoryRepository(
      KeepOpenedInventoryRepository keepOpenedInventoryRepository
    ) {
      this.keepOpenedInventoryRepository = keepOpenedInventoryRepository;
      return this;
    }

    public Builder withInventory(Inventory inventory) {
      this.inventory = inventory;
      return this;
    }

    public Builder withUserId(UUID userId) {
      this.userId = userId;
      return this;
    }

    public OpenedInventory create() {
      Preconditions.checkNotNull(keepOpenedInventoryRepository,
        "keepOpenedInventoryRepository");
      Preconditions.checkNotNull(inventory, "inventory");
      Preconditions.checkNotNull(userId, "userId");
      return new OpenedInventory(
        keepOpenedInventoryRepository,
        inventory,
        userId);
    }
  }
}