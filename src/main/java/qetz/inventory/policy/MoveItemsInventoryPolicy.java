package qetz.inventory.policy;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MoveItemsInventoryPolicy implements InventoryPolicy {
  Allow,
  Deny;

  @Override
  public void apply(InventoryClickEvent click) {
    switch (this) {
      case Allow -> click.setCancelled(false);
      case Deny -> click.setCancelled(true);
    }
  }
}