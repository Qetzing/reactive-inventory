package qetz.inventory.open;

import com.google.inject.AbstractModule;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import qetz.components.Component;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public final class OpenInventoryModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(OpenInventoryRepository.class).toInstance(
      OpenInventoryRepository.empty()
    );
  }
}