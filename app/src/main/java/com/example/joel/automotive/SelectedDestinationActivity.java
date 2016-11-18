package com.example.joel.automotive;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.location.Address;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.math.BigInteger;

import ethereumjava.solidity.types.SUInt;

/**
 * Created by alb on 13/10/16.
 * Activity permitting pre-computed destination selection
 */

public class SelectedDestinationActivity extends AppCompatActivity implements View.OnClickListener {
// Pour passer outre la map et proposer 5 trajets précalculés
// Doit se placer entre DetectedCarActivity et Summary Activity
//    necessite les informations "car".


    private Car car;
    private Destination destination;
    private Address hereAddress;


    private AppCompatButton go_sqli;
    private AppCompatButton go_cantine;
    private AppCompatButton go_bar;
    private AppCompatButton go_lego;
    private AppCompatButton go_tardis;
    private String origin;
    private double amount;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_destination);

        go_sqli = (AppCompatButton) findViewById(R.id.btn_sqli);
        go_cantine = (AppCompatButton) findViewById(R.id.btn_cantine);
        go_bar = (AppCompatButton) findViewById(R.id.btn_bar);
        go_tardis = (AppCompatButton) findViewById(R.id.btn_tardis);
        go_lego = (AppCompatButton) findViewById(R.id.btn_lego);
        car = (Car) getIntent().getExtras().getSerializable(Constants.CAR);
        origin = ("Cité des Congrès");


        //dest = (Address) destinationAddress;
        go_sqli.setOnClickListener(this);
        go_cantine.setOnClickListener(this);
        go_bar.setOnClickListener(this);
        go_tardis.setOnClickListener(this);
        go_lego.setOnClickListener(this);

    }

    private void GoTo(final int x, final int y){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final MyApplication application = (MyApplication) getApplication();
                boolean unlocked = application.ethereumjava.personal.unlockAccount(application.accountId,MyApplication.PASSWORD,3600);
                if( unlocked ) {
                    application.choupetteContract.GoTo(
                            SUInt.fromBigInteger256(BigInteger.valueOf(x)),
                            SUInt.fromBigInteger256(BigInteger.valueOf(y)))
                            .sendTransaction(application.accountId, new BigInteger("90000"));
                }
            }
        }).start();
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Cette action va arrêter la transaction")
                .setMessage("Retour à l'accueil?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface arg0, int arg1) {
                        goToMainActivity();
                    }
                }).create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    public void onClick(View v) {

        String destinationString = "";
        int x = 0,y = 0;

        if( v == go_sqli){
            destinationString = "Agence SQLI Nantes";
            x = 3;
            y = 2;
        } else if( v == go_cantine ){
            destinationString = "La cantine du numerique";
            x = 8;
            y = 3;
        }else if( v == go_bar ){
            destinationString = "Le dernier bar avant la fin du monde (Paris I)";
            x = 10;
            y = 11;
        }else if( v == go_lego ){
            destinationString = "LegoLand (Danemark)";
            x = 12;
            y = 1;
        }else if( v == go_tardis ){
            destinationString = "Le Tardis (London, England)";
            x = 10;
            y = 90;
        } else{
            return;
        }

        GoTo(x,y);

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(SelectedDestinationActivity.this, SummaryActivity.class);
        intent.putExtra(Constants.CAR, car);
        intent.putExtra(Constants.FROM, origin);
        intent.putExtra(Constants.DESTINATION, destinationString);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
