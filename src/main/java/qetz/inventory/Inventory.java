package qetz.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public interface Inventory {
  void atInteract(InventoryClickEvent click, Player target, UUID userId);
  void open(Player target);
}