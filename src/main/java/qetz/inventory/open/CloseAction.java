package qetz.inventory.open;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import qetz.inventory.actions.InventoryAction;

import java.util.Collection;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
public final class CloseAction implements InventoryAction {
  private final OpenInventoryRepository repository;

  @Override
  public ExecutableAction asExecutable() {
    return new CloseExecutable(repository);
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class CloseExecutable implements ExecutableAction {
    private final OpenInventoryRepository repository;
    private OpenInventory inventory;

    @Override
    public ExecutableAction withTarget(OpenInventory inventory) {
      this.inventory = inventory;
      return null;
    }

    @Override
    public void perform() {
      Preconditions.checkNotNull(inventory, "inventory");
      repository.remove(inventory.userId());
      inventory.close();
    }
  }
}