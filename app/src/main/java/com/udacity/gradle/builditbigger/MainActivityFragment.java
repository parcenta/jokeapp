package com.udacity.gradle.builditbigger;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.databinding.FragmentMainBinding;

import java.io.IOException;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>{

    // Saved Instance.
    private static String IS_FETCHING_JOKES = "IS_FETCHING_JOKES";
    private boolean isFetchingJokes;

    // For Loader
    private static int FETCH_JOKES_LOADER_ID = 4545;

    private FragmentMainBinding mBinding;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);


        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mBinding.adView.loadAd(adRequest);

        // Set OnClickListener when user clicks in Tell joke.
        mBinding.tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLoaderManager().restartLoader(FETCH_JOKES_LOADER_ID,null,MainActivityFragment.this);
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Managing the isFetchingJokes flag
        if(savedInstanceState!=null && savedInstanceState.containsKey(IS_FETCHING_JOKES)){
            isFetchingJokes = savedInstanceState.getBoolean(IS_FETCHING_JOKES);
            if(isFetchingJokes){
                mBinding.loadingJokeContainer.setVisibility(View.VISIBLE);
                getLoaderManager().initLoader(FETCH_JOKES_LOADER_ID,null,this);
            }
        }else
            isFetchingJokes = false;
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(getActivity()) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                mBinding.loadingJokeContainer.setVisibility(View.VISIBLE);
                isFetchingJokes = true;
                forceLoad();
            }

            @Override
            public String loadInBackground() {

                MyApi myApiService = null;

                if(myApiService == null) {  // Only do this once
                    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), null)
                            // options for running against local devappserver
                            // - 10.0.2.2 is localhost's IP address in Android emulator
                            // - turn off compression when running against local devappserver
                            .setApplicationName("BuildItBigger")
                            .setRootUrl("http://192.168.0.105:9091/_ah/api/")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                    abstractGoogleClientRequest.setDisableGZipContent(true);
                                }
                            });
                    // end options for devappserver

                    myApiService = builder.build();
                }

                try {
                    return myApiService.tellRandomJoke().execute().getData();
                } catch (Exception e) {
                    return e.getMessage();
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        isFetchingJokes = false;
        mBinding.loadingJokeContainer.setVisibility(View.GONE);
        Toast.makeText(getActivity(),data,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_FETCHING_JOKES,isFetchingJokes);
    }
}
