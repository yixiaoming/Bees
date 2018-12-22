package org.yxm.bees.entity.wan;

public class WanBaseEntity<T> {

    public static final int ERRCODE_SUCCESS = 0;

    public int errorCode;
    public String errorMsg;

    public T data;

    @Override
    public String toString() {
        return "WanBaseEntity{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
