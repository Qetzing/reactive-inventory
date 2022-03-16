package qetz.inventory.open;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import qetz.inventory.actions.InventoryAction;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
public final class TriggerInteractAction implements InventoryAction {
  private InventoryClickEvent click;

  public TriggerInteractAction withInventoryClick(InventoryClickEvent click) {
    this.click = click;
    return this;
  }

  @Override
  public ExecutableAction asExecutable() {
    Preconditions.checkNotNull(click, "click");
    return new InteractExecutable(click);
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class InteractExecutable implements ExecutableAction {
    private final InventoryClickEvent click;

    private OpenInventory inventory;

    @Override
    public ExecutableAction withTarget(OpenInventory inventory) {
      this.inventory = inventory;
      return this;
    }

    @Override
    public void perform() {
      Preconditions.checkNotNull(inventory, "inventory");
      inventory.triggerInteract(click);
    }
  }
}