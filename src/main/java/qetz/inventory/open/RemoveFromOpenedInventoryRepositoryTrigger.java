package qetz.inventory.open;

import com.google.inject.Inject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import qetz.components.Component;

@Component
public final class RemoveFromOpenedInventoryRepositoryTrigger
  implements Listener
{
  private final KeepOpenedInventoryRepository keepOpenedInventoryRepository;
  private final OpenedInventoryRepository openedInventoryRepository;

  @Inject
  private RemoveFromOpenedInventoryRepositoryTrigger(
    KeepOpenedInventoryRepository keepOpenedInventoryRepository,
    OpenedInventoryRepository openedInventoryRepository
  ) {
    this.keepOpenedInventoryRepository = keepOpenedInventoryRepository;
    this.openedInventoryRepository = openedInventoryRepository;
  }

  @EventHandler
  private void removeFromOpenedInventoryRepository(InventoryCloseEvent close) {
    var userId = close.getPlayer().getUniqueId();

    if (keepOpenedInventoryRepository.shouldOpenedInventoryKeep(userId)) {
      keepOpenedInventoryRepository.remove(userId);
      return;
    }
    openedInventoryRepository.removeOpenedInventory(userId);
  }

  @EventHandler
  private void removeFromOpenedInventoryRepository(PlayerQuitEvent quit) {
    var userId = quit.getPlayer().getUniqueId();

    openedInventoryRepository.removeOpenedInventory(userId);
  }
}