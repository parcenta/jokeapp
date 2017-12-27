package com.udacity.gradle.builditbigger;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.gradle.builditbigger.databinding.FragmentMainBinding;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment{

    // MyApi
    private FetchJokeAsyncTask mAsyncTask;

    // Binding
    private FragmentMainBinding mBinding;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        // Set OnClickListener when user clicks in Tell joke.
        mBinding.tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAsyncTask!=null && !mAsyncTask.isCancelled()) mAsyncTask.cancel(true);
                mAsyncTask = new FetchJokeAsyncTask();
                mAsyncTask.execute(getActivity());
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAsyncTask!=null && !mAsyncTask.isCancelled()) mAsyncTask.cancel(true);
    }
}
