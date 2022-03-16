package qetz.inventory.open;

import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import qetz.components.Component;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
@Component
public final class RemoveOpenedInventoryTrigger implements Listener {
  private final OpenInventoryRepository openedInventories;

  @EventHandler
  private void removeOnClose(InventoryCloseEvent close) {
    var userId = close.getPlayer().getUniqueId();

    openedInventories.remove(userId);
  }

  @EventHandler
  private void removeOnQuit(PlayerQuitEvent quit) {
    var userId = quit.getPlayer().getUniqueId();

    openedInventories.remove(userId);
  }
}