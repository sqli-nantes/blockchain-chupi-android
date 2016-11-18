package com.example.joel.automotive;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

//  Propose le choix de detection des véhicules
// Choix : QR Code ou BlueTouch
// entre SplashScreen et LoadingActivity ou QRScanActivity

public class ScanningChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_choice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//      Pour le DevFest, la possibilité blueTouch a été éliminée
//      FloatingActionButton fab_bt = (FloatingActionButton)findViewById(R.id.fab_search_around);
        FloatingActionButton fab_qr = (FloatingActionButton)findViewById(R.id.fab_qr_code);
//
//      Relatif au blueTOUCH
//        fab_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ScanningChoiceActivity.this,LoadingActivity.class);
//                startActivity(intent);
//                overridePendingTransition(0,0);
//            }
//        });

        fab_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ScanningChoiceActivity.this,QrScanActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
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
