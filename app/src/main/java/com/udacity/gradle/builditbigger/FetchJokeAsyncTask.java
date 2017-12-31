package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.testing.JokeAppIdlingResourceHandler;

import java.io.IOException;

import javalibraryexercise.peterarct.com.jokeandroidlibrary.JokeDisplayActivity;

// --------------------------------------
// Async Task for Fetching jokes.
// --------------------------------------
public class FetchJokeAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private JokeAppIdlingResourceHandler mIdlingResourceHandler;

    FetchJokeAsyncTask(JokeAppIdlingResourceHandler idlingResourceHandler){
        this.mIdlingResourceHandler = idlingResourceHandler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // Mark that the app is in not IDLE. (For Testing)
        if (mIdlingResourceHandler!=null) mIdlingResourceHandler.setIdle(false);
    }

    @Override
    protected String doInBackground(Context... contexts) {

        // Get Context.
        context = contexts[0];

        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://192.168.1.182:9091/_ah/api/")
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

    @Override
    protected void onPostExecute(String response) {
        // Now mark that the app is IDLE (For Testing)
        if (mIdlingResourceHandler!=null) mIdlingResourceHandler.setIdle(true);

        // Display the Joke in the activity.
        JokeDisplayActivity.launch(context,response);
    }
}
