package com.example.joel.automotive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.math.BigInteger;

import ethereumjava.module.objects.Transaction;
import ethereumjava.solidity.types.SUInt;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by joel on 01/08/16.
 */

public class TravelActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inprogress);


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(5000);

                    GoToArrivedActivity();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

/*        final MyApplication application = (MyApplication) getApplication();
        boolean unlocked = application.ethereumjava.personal.unlockAccount(application.accountId,MyApplication.PASSWORD,3600);
        if( unlocked ) {
            Observable<SUInt.SUInt256> onStateChangedObservable = application.choupetteContract.OnStateChanged().watch();
            onStateChangedObservable.subscribe(new Subscriber<SUInt.SUInt256>() {
                @Override
                public void onCompleted() {
                    Log.e("GETH","ONSTATECHANGED -- COMPLETED");
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("GETH",e.getMessage());
                }

                @Override
                public void onNext(SUInt.SUInt256 res) {
                    if( res != null ) {
                        Log.d("GETH", res.asString());
                        GoToArrivedActivity();
                    }else{
                        Log.e("GETH","STATE NULL");
                    }
                }
            });
        }*/

    }

    private void GoToArrivedActivity(){
        Intent intent = new Intent(TravelActivity.this, ArrivedActivity.class);
        startActivity(intent);
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

}
