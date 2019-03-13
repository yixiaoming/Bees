package org.yxm.bees.module.main;

import dagger.Component;
import org.yxm.bees.ioc.ApplicationScope;
import org.yxm.bees.ioc.EngineModule;
import org.yxm.bees.ioc.GsonModule;

@ApplicationScope
@Component(modules = {GsonModule.class, EngineModule.class})
public interface MainActivityComponent {

  void injectMainActivity(MainActivity activity);
}
