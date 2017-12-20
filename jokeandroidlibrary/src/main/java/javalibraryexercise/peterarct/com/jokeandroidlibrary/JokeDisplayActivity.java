package javalibraryexercise.peterarct.com.jokeandroidlibrary;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    }
}
