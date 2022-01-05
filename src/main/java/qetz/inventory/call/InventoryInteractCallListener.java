package qetz.inventory.call;

import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import qetz.components.Component;
import qetz.inventory.open.OpenedInventoryRepository;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
public final class InventoryInteractCallListener implements Listener {
  private final OpenedInventoryRepository openedInventoryRepository;

  @EventHandler
  private void callInventoryInteract(InventoryClickEvent click) {
    var userId = click.getWhoClicked().getUniqueId();

    openedInventoryRepository.findOpenedInventory(userId)
      .ifPresent(openedInventory -> {
        if (openedInventory.continueAndApplyPolicies(click)) {
          openedInventory.triggerInteract(click);
        }
      });
  }
}