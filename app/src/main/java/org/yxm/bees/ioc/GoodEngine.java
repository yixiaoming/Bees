package org.yxm.bees.ioc;

import javax.inject.Inject;

public class GoodEngine extends Engine {

  @Inject
  public GoodEngine() {
  }

  @Override
  public void work() {
    System.out.println("good engine");
  }
}
