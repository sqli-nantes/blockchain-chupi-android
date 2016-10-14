package com.example.joel.automotive;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.location.Address;
import android.support.design.widget.FloatingActionButton;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static com.example.joel.automotive.R.string.sqli_n;

/**
 * Created by alb on 13/10/16.
 * Activity permitting pre-computed destination selection
 */

public class SelectedDestinationActivity extends AppCompatActivity {
// Pour passer outre la map et proposer 5 trajets précalculés
// Doit se placer entre DetectedCarActivity et Summary Activity

    private Car car;
    private Destination destination;
    private Address hereAddress;

    /* private FloatingActionButton fab_sqli;
     private FloatingActionButton fab_cantine;
     private FloatingActionButton fab_bar;
     private FloatingActionButton fab_lego;
     private FloatingActionButton fab_tardis; */
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
//    private GoogleApiClient client2;


    //    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_destination);

     /*   fab_sqli = (FloatingActionButton)findViewById(R.id.btn_sqli);
        fab_cantine = (FloatingActionButton)findViewById(R.id.btn_cantine);
        fab_bar = (FloatingActionButton)findViewById(R.id.btn_bar);
        fab_tardis = (FloatingActionButton)findViewById(R.id.btn_tardis);
        fab_lego = (FloatingActionButton)findViewById(R.id.btn_lego);
        car = (Car) getIntent().getExtras().getSerializable(Constants.CAR);


        //dest = (Address) destinationAddress;
        fab_sqli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectedDestinationActivity.this, SummaryActivity.class);
                intent.putExtra(Constants.CAR, car);
                intent.putExtra(Constants.FROM, origin);
                intent.putExtra(Constants.DESTINATION, "Agence SQLI Nantes");
                intent.putExtra(Constants.PRICE, 12000);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        fab_cantine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectedDestinationActivity.this, SummaryActivity.class);
                intent.putExtra(Constants.CAR, car);
                intent.putExtra(Constants.FROM, origin);
                intent.putExtra(Constants.DESTINATION, "La cantine du numerique");
                intent.putExtra(Constants.PRICE, 600);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        fab_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectedDestinationActivity.this, SummaryActivity.class);
                intent.putExtra(Constants.CAR, car);
                intent.putExtra(Constants.FROM, origin);
                intent.putExtra(Constants.DESTINATION, "Le dernier bar avant la fin du monde (Paris I)");
                intent.putExtra(Constants.PRICE, 382000);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        fab_tardis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectedDestinationActivity.this, SummaryActivity.class);
                intent.putExtra(Constants.CAR, car);
                intent.putExtra(Constants.FROM, origin);
                intent.putExtra(Constants.DESTINATION, "Le Tardis (London)");
                intent.putExtra(Constants.PRICE, 778655);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        fab_lego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectedDestinationActivity.this, SummaryActivity.class);
                intent.putExtra(Constants.CAR, car);
                intent.putExtra(Constants.FROM, origin);
                intent.putExtra(Constants.DESTINATION, "LegoLand (Danemark)");
                intent.putExtra(Constants.PRICE, 1553000);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
         */

        go_sqli = (AppCompatButton) findViewById(R.id.btn_sqli);
        go_cantine = (AppCompatButton) findViewById(R.id.btn_cantine);
        go_bar = (AppCompatButton) findViewById(R.id.btn_bar);
        go_tardis = (AppCompatButton) findViewById(R.id.btn_tardis);
        go_lego = (AppCompatButton) findViewById(R.id.btn_lego);
        car = (Car) getIntent().getExtras().getSerializable(Constants.CAR);
        origin = ("Cité des congrès");


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
//                destination.setDestinationName("La cantine du numerique");
                intent.putExtra(Constants.CAR, car);
                intent.putExtra(Constants.FROM, origin);
                intent.putExtra(Constants.DESTINATION, "La cantine du numerique");
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
                intent.putExtra(Constants.DESTINATION, "Le dernier bar avant la fin du monde (Paris I)");
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
                intent.putExtra(Constants.DESTINATION, "Le Tardis (London)");
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
                intent.putExtra(Constants.DESTINATION, "LegoLand (Danemark)");
                intent.putExtra(Constants.PRICE, 1553.0);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    // Liste des adresses possible mis en dur dans le code pour demo, à enlever ensuite!
    /*private void DestinationDisplay (Address destinationAddress){
        Address AgenceSqli;
        Address CantineNumérique;
        Address DernierBar;
        Address MuseeLego;
        Address Tardis;

        AgenceSqli.setFeatureName("Agence SQLI Nantes");

    }*/


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
