package com.example.joel.automotive;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import ethereumjava.module.objects.Hash;
import ethereumjava.solidity.SolidityUtils;
import ethereumjava.solidity.types.SUInt;

/**
 * Created by joel on 11/08/16.
 */
public class SummaryActivity extends AppCompatActivity {
    private TextView from;
    private TextView destination;
    private TextView price;
    private ImageView car_img;
    private Button btn_accept;
    private Button btn_cancel;
    private Bundle bundle;


    BigInteger priceValue;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.activity_summary);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.removeItem(R.id.action_refresh);
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

        if (id == R.id.action_back) {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    private void goToMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    @Override
    protected void onStart() {
        super.onStart();

        GetPrice();

        setContentView(R.layout.activity_summary);

        from = (TextView) findViewById(R.id.txt_from);
        destination = (TextView) findViewById(R.id.txt_destination);
        price = (TextView) findViewById(R.id.txt_price);
        car_img = (ImageView) findViewById(R.id.car_img);
        btn_accept = (Button) findViewById(R.id.btn_accept);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartRent();

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(SummaryActivity.this,TravelActivity.class);
                startActivity(intent);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        bundle = getIntent().getExtras();

        if (bundle!=null){
            from.setText(bundle.getString(Constants.FROM).replaceAll("\\+"," "));
            destination.setText(bundle.getString(Constants.DESTINATION).replaceAll("\\+"," "));

            GetPrice();

            car_img.setImageBitmap(((Car) bundle.getSerializable(Constants.CAR)).getImage());
        }
    }

    private void StartRent(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final MyApplication application = (MyApplication) getApplication();
                boolean unlocked = application.ethereumjava.personal.unlockAccount(application.accountId, MyApplication.PASSWORD, 3600);
                if (unlocked) {
                    application.choupetteContract.StartRent().sendTransaction(application.accountId, new BigInteger("90000"));
                }
            }
        }).start();
    }


    private void GetPrice(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final MyApplication application = (MyApplication) getApplication();
                boolean unlocked = application.ethereumjava.personal.unlockAccount(application.accountId, MyApplication.PASSWORD, 3600);
                if (unlocked) {
                    SUInt.SUInt256 result = (SUInt.SUInt256)application.choupetteContract.GetPrice().call();
                    priceValue = (BigInteger) result.get();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NumberFormat nf = NumberFormat.getCurrencyInstance();
                            price.setText(nf.format(priceValue.intValue()));
                        }
                    });
                }
            }
        }).start();
    }
}
