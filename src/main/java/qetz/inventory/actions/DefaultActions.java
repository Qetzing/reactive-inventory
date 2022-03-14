package qetz.inventory.actions;

import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import qetz.inventory.open.CloseAction;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
public final class DefaultActions {
  private final CloseAction action;

  public CloseAction close() {
    return action;
  }
}