package org.yxm.bees.ioc;

import javax.inject.Inject;

public class Car {

  private Engine engine;

  @Inject
  public Car(@GoodEngineFlag Engine engine) {
    this.engine = engine;
  }

  public void run() {
    engine.work();
  }
}
