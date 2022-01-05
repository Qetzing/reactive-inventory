package qetz.inventory.open;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class KeepOpenedInventoryRepository {
  public static KeepOpenedInventoryRepository create() {
    return new KeepOpenedInventoryRepository(Lists.newArrayList());
  }

  private final Collection<UUID> keepOpenedInventories;

  public void add(UUID userId) {
    Preconditions.checkNotNull(userId, "userId");
    keepOpenedInventories.add(userId);
  }

  public void remove(UUID userId) {
    Preconditions.checkNotNull(userId, "userId");
    keepOpenedInventories.remove(userId);
  }

  public boolean shouldOpenedInventoryKeep(UUID userId) {
    Preconditions.checkNotNull(userId, "userId");
    return keepOpenedInventories.contains(userId);
  }
}