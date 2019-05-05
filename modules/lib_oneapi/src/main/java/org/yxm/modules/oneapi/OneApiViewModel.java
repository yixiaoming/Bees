package org.yxm.modules.oneapi;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import java.util.List;

public class OneApiViewModel extends ViewModel {

  private MutableLiveData<List<String>> mAuthers;

  public MutableLiveData<List<String>> getAuthers() {
    return mAuthers;
  }
}
