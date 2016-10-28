package com.example.joel.automotive;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.*;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by joel on 01/08/16.
 */

public class TravelActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inprogress);

        AppCompatButton btn = (AppCompatButton) findViewById(R.id.btn_destination) ;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TravelActivity.this, ArrivedActivity.class);
                startActivity(intent);
            }
        });

////        Tentative d'ajout de gif anime. Test 1 . Source : http://droid-blog.net/2011/10/14/tutorial-how-to-use-animated-gifs-in-android-part-1/
//
//        InputStream stream = null;
//        try {
//            stream = getAssets().open("@drawable/routesansfin.gif");
//        } catch (IOException e) {
//        }
//            e.printStackTrace();
//        GifMovieView view = new GifMovieView(this, stream);
//        setContentView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.removeItem(R.id.action_refresh);
        menu.removeItem(R.id.action_back);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            goToMainActivity();
        }

        return super.onOptionsItemSelected(item);
    }


    private void goToMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

////    Tentative d'ajout de gif anime. Test 1 . Source : http://droid-blog.net/2011/10/14/tutorial-how-to-use-animated-gifs-in-android-part-1/
//    public class GifMovieView extends View {
//        private Movie mMovie;
//        private Context context;
//        private InputStream stream;
//        private InputStream mStream;
//
//        public GifMovieView(Context context, InputStream stream) {
//            super(context);
//            mStream = stream;
//            mMovie = Movie.decodeStream(mStream);
//        }
//
//        private long mMoviestart;
//
//        @Override
//        protected void onDraw(Canvas canvas) {
//            canvas.drawColor(Color.TRANSPARENT);
//            super.onDraw(canvas);
//            final long now = SystemClock.uptimeMillis();
//            if (mMoviestart == 0) {
//                mMoviestart = now;
//            }
//            final int relTime = (int)((now - mMoviestart) % mMovie.duration());
//            mMovie.setTime(relTime);
//            mMovie.draw(canvas, 10, 10);
//            this.invalidate();
//        }
//    }

}
