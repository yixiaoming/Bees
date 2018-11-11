package org.yxm.bees.module.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseMvpFragment;
import org.yxm.lib.volley.VolleyManager;

/**
 * Created by yxm on 2018.8.11.
 */

public class PersonalFragment extends BaseMvpFragment<IPersonalView, PersonalPresenter>
        implements IPersonalView {

    public static final String TAG = "PersonalFragment";

    @Override
    protected PersonalPresenter createPresenter() {
        return new PersonalPresenter();
    }

    public static PersonalFragment newInstance() {
        PersonalFragment fragment = new PersonalFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.personal_fragment_layout, container, false);
        initViews(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews(View root) {

    }

    @Override
    public void onResume() {
        super.onResume();
        VolleyManager
                .getInstance()
                .getRequest("http://www.wanandroid.com/tools/mockapi/3974/testjson")
                .listener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        System.out.println(response);
                    }
                })
                .errorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.getMessage());
                        System.out.println(error);
                    }
                })
                .start();
    }
}
