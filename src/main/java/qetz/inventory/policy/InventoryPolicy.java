package qetz.inventory.policy;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface InventoryPolicy {
  void apply(InventoryClickEvent click);
}