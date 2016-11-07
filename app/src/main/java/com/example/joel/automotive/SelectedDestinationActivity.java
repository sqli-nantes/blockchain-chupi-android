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

/**
 * Created by alb on 13/10/16.
 * Activity permitting pre-computed destination selection
 */

public class SelectedDestinationActivity extends AppCompatActivity {
// Pour passer outre la map et proposer 5 trajets précalculés
// Doit se placer entre DetectedCarActivity et Summary Activity
//    necessite les informations car


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
        go_sqli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectedDestinationActivity.this, SummaryActivity.class);

//                destination.setDestinationName(getResources().getString(R.string.sqli_n));
                intent.putExtra(Constants.CAR, car);
                intent.putExtra(Constants.FROM, origin);
                intent.putExtra(Constants.DESTINATION, "Agence SQLI Nantes");
                intent.putExtra(Constants.PRICE, 12.0);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        go_cantine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectedDestinationActivity.this, SummaryActivity.class);
//                destination.setDestinationName("La Cantine numérique");
                intent.putExtra(Constants.CAR, car);
                intent.putExtra(Constants.FROM, origin);
                intent.putExtra(Constants.DESTINATION, "La Cantine numérique (Nantes)");
                intent.putExtra(Constants.PRICE, 0.6);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        go_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectedDestinationActivity.this, SummaryActivity.class);
                intent.putExtra(Constants.CAR, car);
                intent.putExtra(Constants.FROM, origin);
                intent.putExtra(Constants.DESTINATION, "Le Dernier Bar avant la Fin du Monde (Paris I)");
                intent.putExtra(Constants.PRICE, 382.0);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        go_tardis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectedDestinationActivity.this, SummaryActivity.class);
                intent.putExtra(Constants.CAR, car);
                intent.putExtra(Constants.FROM, origin);
                intent.putExtra(Constants.DESTINATION, "Le Tardis (London - England)");
                intent.putExtra(Constants.PRICE, 778.65);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        go_lego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectedDestinationActivity.this, SummaryActivity.class);
                intent.putExtra(Constants.CAR, car);
                intent.putExtra(Constants.FROM, origin);
                intent.putExtra(Constants.DESTINATION, "LegoLand (Billung - Danemark)");
                intent.putExtra(Constants.PRICE, 1553.0);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

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
}
