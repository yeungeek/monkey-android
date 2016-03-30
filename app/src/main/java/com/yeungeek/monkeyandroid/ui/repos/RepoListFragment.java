package com.yeungeek.monkeyandroid.ui.repos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yeungeek.monkeyandroid.R;
import com.yeungeek.monkeyandroid.data.model.Language;

/**
 * Created by yeungeek on 2016/3/30.
 */
public class RepoListFragment extends Fragment {
    Language language;

    private TextView mName;

    public static Fragment newInstance(Context context, Language language, String timeSpan) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("extra_language", language);
        bundle.putSerializable("extra_time_span", timeSpan);
        return Fragment.instantiate(context, RepoListFragment.class.getName(), bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        language = (Language) getArguments().getSerializable("extra_language");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);
        mName = (TextView) view.findViewById(R.id.name);
        if (null != language) {
            mName.setText(language.name);
        }
        return view;
    }
}
