package org.yxm.lib.annotation.test.runtime;

public class TestGet {

  @Get(value = "192.168.1.1")
  public String getIp() {
    return "";
  }

}
