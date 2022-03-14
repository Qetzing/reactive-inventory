package qetz.inventory.open;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import qetz.inventory.actions.InventoryAction;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
public final class TriggerUpdateAction implements InventoryAction {
  @Override
  public ExecutableAction asExecutable() {
    return new InteractExecutable();
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class InteractExecutable implements ExecutableAction {
    private OpenInventory inventory;

    @Override
    public ExecutableAction withTarget(OpenInventory inventory) {
      this.inventory = inventory;
      return null;
    }

    @Override
    public void perform() {
      Preconditions.checkNotNull(inventory, "inventory");
      inventory.update();
    }
  }
}