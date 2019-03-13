package org.yxm.bees.ioc;

import dagger.Module;
import dagger.Provides;

@Module
public class EngineModule {

  @Provides
  @GoodEngineFlag
  public Engine provideGoodEngine() {
    return new GoodEngine();
  }

  @Provides
  @BadEngineFlag
  public Engine provideBadEngine() {
    return new BadEngine();
  }

}
