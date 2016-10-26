package com.example.joel.automotive;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by joel on 04/08/16.
 * Ceci est la copie du mainActivity initial
 */

//  Lancement de l'appli et gestion de la première activité : choix entre QR code et BlueTouch
//
//

//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
//        getSupportActionBar().show();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//  Pour le DevFest, la possibilité blueTouch a été éliminée
//        FloatingActionButton fab_bt = (FloatingActionButton)findViewById(R.id.fab_search_around);
        FloatingActionButton fab_qr = (FloatingActionButton)findViewById(R.id.fab_qr_code);

        fab_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,QrScanActivity.class);
                startActivity(intent);
//                overridePendingTransition(0,0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.removeItem(R.id.action_back);
        menu.removeItem(R.id.action_refresh);
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
