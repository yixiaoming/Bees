package org.yxm.bees.model;

/**
 * Created by yxm on 2018.7.29.
 */

public interface IKaiyanModel {

    void getCategories(LoadDataListener listener);

    interface LoadDataListener<T> {
        void onSuccess(T data);

        void onFailed(Throwable t);
    }
}
