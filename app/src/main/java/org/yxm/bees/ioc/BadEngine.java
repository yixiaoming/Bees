package org.yxm.bees.ioc;

import javax.inject.Inject;

public class BadEngine extends Engine {

  @Inject
  public BadEngine() {
  }

  @Override
  public void work() {
    System.out.println("bad engine");
  }
}
