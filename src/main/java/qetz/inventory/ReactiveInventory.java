package qetz.inventory;

import org.bukkit.entity.Player;

public interface ReactiveInventory extends Inventory {
  void openUpdated(Player target);
}