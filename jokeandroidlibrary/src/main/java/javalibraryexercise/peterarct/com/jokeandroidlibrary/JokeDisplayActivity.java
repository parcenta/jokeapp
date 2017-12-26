package javalibraryexercise.peterarct.com.jokeandroidlibrary;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import javalibraryexercise.peterarct.com.jokeandroidlibrary.databinding.ActivityJokeDisplayBinding;

public class JokeDisplayActivity extends AppCompatActivity {

    public static String JOKE_EXTRA = "JOKE_EXTRA";

    ActivityJokeDisplayBinding mBinding;

    /* -----------------------------------------------------
       LAUNCHER HELPERS
     ------------------------------------------------------ */
    public static void launch(Context context, String joke){
        Intent intent = new Intent(context,JokeDisplayActivity.class);
        intent.putExtra(JOKE_EXTRA,joke);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_joke_display);

        String joke = getIntent().getStringExtra(JOKE_EXTRA);
        mBinding.displayJokeTextView.setText(joke);

        ActionBar ab = getSupportActionBar();
        if (ab!=null) ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
