package org.yxm.lib.annotation.test.runtime;

import java.lang.reflect.Method;

public class RuntimeAnnotationProcessor {

  public void test() {
    TestGet testGet = new TestGet();

    Method[] methods = testGet.getClass().getDeclaredMethods();
    for (Method m : methods) {
      Get get = m.getAnnotation(Get.class);
    }
  }
}
