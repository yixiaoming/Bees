package org.yxm.modules.wan;

import android.os.Handler;
import android.os.Looper;
import java.util.List;
import org.yxm.lib.async.ThreadManager;
import org.yxm.modules.base.mvp.BasePresenter;
import org.yxm.modules.wan.entity.WanTabEntity;
import org.yxm.modules.wan.repo.local.IWanModel;
import org.yxm.modules.wan.repo.local.WanModel;

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
        ThreadManager.getInstance().runIo(new Runnable() {
            @Override
            public void run() {
                final List<WanTabEntity> tabs = mModel.getWanTabs();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (tabs != null) {
                            mView.get().onInitTabLayout(tabs);
                        }
                    }
                });
            }
        });

    }
}
