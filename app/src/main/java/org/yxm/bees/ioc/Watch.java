package org.yxm.bees.ioc;

import android.util.Log;
import javax.inject.Inject;

public class Watch {

  private static final String TAG = "Watch";

  private Engine engine;

  @Inject
  public Watch(@BadEngineFlag Engine engine) {
    this.engine = engine;
  }

  public void test() {
    Log.d(TAG, "test: " + engine.hashCode());
  }
}
