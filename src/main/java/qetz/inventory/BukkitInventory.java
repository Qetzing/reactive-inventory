package qetz.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import qetz.inventory.policy.InventoryPolicy;
import qetz.inventory.policy.MoveItemsInventoryPolicy;

import java.util.Set;

public abstract class BukkitInventory implements Inventory, Cloneable {
  private final Set<InventoryPolicy> policies;
  private final String id;

  public BukkitInventory(
    Set<InventoryPolicy> policies,
    String id
  ) {
    this.policies = policies;
    this.id = id;
  }

  public String id() {
    return id;
  }

  @SuppressWarnings("ConstantConditions")
  public boolean continueWithEvent(InventoryClickEvent click) {
    return click.getInventory() != null
      && click.getCurrentItem() != null
      && click.getCurrentItem().getType() != null
      && click.getCurrentItem().getType() != Material.AIR
      && click.getCursor() != null
      && click.getWhoClicked() instanceof Player;
  }

  public void applyPolicies(InventoryClickEvent click) {
    for (var privacy : policies) {
      applyMoveItemsPolicy(click, privacy);
    }
  }

  private void applyMoveItemsPolicy(
    InventoryClickEvent click,
    InventoryPolicy privacy
  ) {
    if (privacy == MoveItemsInventoryPolicy.DENY) {
      click.setCancelled(true);
    }
  }

  @Override
  protected abstract Object clone() throws CloneNotSupportedException;
}