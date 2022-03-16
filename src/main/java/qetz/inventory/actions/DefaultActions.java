package qetz.inventory.actions;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import qetz.inventory.open.CloseAction;
import qetz.inventory.open.TriggerInteractAction;
import qetz.inventory.open.TriggerUpdateAction;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
public final class DefaultActions {
  private final TriggerInteractAction interact;
  private final TriggerUpdateAction update;
  private final CloseAction close;

  public CloseAction close() {
    return close;
  }

  public TriggerUpdateAction update() {
    return update;
  }

  public TriggerInteractAction triggerInteract(InventoryClickEvent click) {
    Preconditions.checkNotNull(click, "click");
    return interact.withInventoryClick(click);
  }
}