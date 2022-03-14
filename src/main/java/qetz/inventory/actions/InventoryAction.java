package qetz.inventory.actions;

import qetz.inventory.open.OpenInventory;

public interface InventoryAction extends Cloneable {
  ExecutableAction asExecutable();

 interface ExecutableAction {
    ExecutableAction withTarget(OpenInventory inventory);
    void perform();
  }
}