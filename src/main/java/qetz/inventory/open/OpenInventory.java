package qetz.inventory.open;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import qetz.inventory.Inventory;
import qetz.inventory.PlayerNotOnlineException;
import qetz.inventory.PolicyRestrictedInventory;
import qetz.inventory.ReactiveInventory;

import java.util.UUID;

import static qetz.inventory.PolicyRestrictedInventory.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OpenInventory {
  public static OpenInventory with(Inventory inventory, UUID userId) {
    Preconditions.checkNotNull(inventory, "inventory");
    Preconditions.checkNotNull(userId, "userId");
    return new OpenInventory(inventory, userId);
  }

  private final Inventory inventory;
  private final UUID userId;

  UUID userId() {
    return userId;
  }

  void open() {
    inventory.open(target());
  }

  private Player target() {
    var target = Bukkit.getPlayer(userId);
    if (target == null) {
      throw PlayerNotOnlineException.create();
    }
    return target;
  }

  void close() {
    target().closeInventory();
  }

  void update() {
    if (inventory instanceof ReactiveInventory reactive) {
      var target = target();
      reactive.openUpdated(target);
    }
  }

  boolean isType(Class<? extends Inventory> type) {
    Preconditions.checkNotNull(type, "type");
    return inventory.getClass().isAssignableFrom(type);
  }

  void triggerInteract(InventoryClickEvent click) {
    Preconditions.checkNotNull(click, "click");
    applyPolicies(click);
    callInventoryInteraction(click);
  }

  private void applyPolicies(InventoryClickEvent click) {
    if (inventory instanceof PolicyRestrictedInventory restricted) {
      applySet(restricted.policies(), click);
    }
  }

  private void callInventoryInteraction(InventoryClickEvent click) {
    inventory.interact(
      click,
      (Player) click.getWhoClicked(),
      click.getWhoClicked().getUniqueId()
    );
  }
}