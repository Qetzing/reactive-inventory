package qetz.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public interface Inventory {
  void interact(InventoryClickEvent click, Player target, UUID userId);
  void open(Player target);

  @SuppressWarnings("ConstantConditions")
  static boolean isEventValid(InventoryClickEvent click) {
    return click.getInventory() != null
      && click.getCurrentItem() != null
      && click.getCurrentItem().getType() != null
      && click.getCurrentItem().getType() != Material.AIR
      && click.getCursor() != null
      && click.getWhoClicked() instanceof Player;
  }
}