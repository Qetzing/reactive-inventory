package qetz.inventory;

import org.bukkit.entity.Player;

public interface ReactiveInventory extends Inventory {
  default void openUpdated(Player target) {
    open(target);
  }
}