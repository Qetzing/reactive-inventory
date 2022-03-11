package qetz.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import qetz.inventory.policy.InventoryPolicy;

import java.util.Set;

public interface PolicyRestrictedInventory extends Inventory {
  void applyPolicies(InventoryClickEvent click);

  static void applySet(Set<InventoryPolicy> policies, InventoryClickEvent click) {
    for (var policy : policies) {
      policy.apply(click);
    }
  }
}