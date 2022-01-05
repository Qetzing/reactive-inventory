package qetz.inventory.open;

import com.google.inject.AbstractModule;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import qetz.components.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OpenedInventoryModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(KeepOpenedInventoryRepository.class)
      .toInstance(KeepOpenedInventoryRepository.create());
    bind(OpenedInventoryRepository.class)
      .toInstance(OpenedInventoryRepository.create());
  }
}