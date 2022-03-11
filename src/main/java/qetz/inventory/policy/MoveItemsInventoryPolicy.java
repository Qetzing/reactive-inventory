package qetz.inventory.policy;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MoveItemsInventoryPolicy implements InventoryPolicy {
  ALLOW,
  DENY;

  @Override
  public void apply(InventoryClickEvent click) {
    switch (this) {
      case ALLOW -> click.setCancelled(false);
      case DENY -> click.setCancelled(true);
    }
  }
}