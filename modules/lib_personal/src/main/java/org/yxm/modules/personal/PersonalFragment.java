package org.yxm.modules.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import org.yxm.modules.base.BaseMvpFragment;

/**
 * Created by yxm on 2018.8.11.
 */

public class PersonalFragment extends BaseMvpFragment<IPersonalView, PersonalPresenter>
        implements IPersonalView {

    public static final String TAG = "PersonalFragment";

    private ListView mListView;

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
        View root = inflater.inflate(R.layout.personal_fragment, container, false);
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
    }
}
