package org.yxm.bees.module.wanandroid;

import android.os.Handler;
import android.os.Looper;

import org.yxm.bees.entity.wan.WanTabEntity;
import org.yxm.bees.module.wanandroid.repo.local.IWanModel;
import org.yxm.bees.module.wanandroid.repo.local.WanModel;
import org.yxm.lib.async.ThreadManager;

import java.util.List;
import org.yxm.modules.base.BasePresenter;

/**
 * 与WanFragment关联的Presenter
 */
public class WanPresenter extends BasePresenter<IWanView> {

    private IWanModel mModel;

    public WanPresenter() {
        mModel = new WanModel();
    }

    /**
     * 加载WanFragment Tablayout的数据
     */
    public void initTablayout() {
        ThreadManager.getInstance().runIo(() -> {
            List<WanTabEntity> tabs = mModel.getWanTabs();
            new Handler(Looper.getMainLooper()).post(() -> {
                if (tabs != null) {
                    mView.get().onInitTabLayout(tabs);
                }
            });
        });

    }
}
