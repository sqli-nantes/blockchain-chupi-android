package com.example.joel.automotive;

import android.content.DialogInterface;
import android.content.Intent;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


/**
 * Created by alb on 25/10/16.
 * Appear after Choupette signaled she is arrived. Wait for user's confirm
 */

public class ArrivedActivity extends AppCompatActivity {

//  Pour envoyer les infos de voyage dans la page. Caduque pour le DevFest (actuellement, ne fonctionne pas)

//    private TextView from;
//    private TextView destination;
//    private TextView price;
//    private Bundle bundle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrived);
//        bundle = getIntent().getExtras();
        AppCompatButton btn = (AppCompatButton) findViewById(R.id.btn_destination);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArrivedActivity.this, EndActivity.class);
                startActivity(intent);
            }
        });
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


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Cette action va arrêter la transaction")
                .setMessage("Retour à l'accueil?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface arg0, int arg1) {
                        ArrivedActivity.super.onBackPressed();
                    }
                }).create().show();
    }

//  Pour envoyer les infos de voyage dans la page. Caduque pour le DevFest (actuellement, ne fonctionne pas)

//    protected void onStart() {
//        super.onStart();
//        setContentView(R.layout.activity_summary);

//        from = (TextView) findViewById(R.id.txt_solid_from);
//        destination = (TextView) findViewById(R.id.txt_solid_to);
//        price = (TextView) findViewById(R.id.txt_solid_price);
//        btn = (AppCompatButton) findViewById(R.id.btn_destination);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ArrivedActivity.this, EndActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        bundle = getIntent().getExtras();
//
//        if (bundle != null) {
//            from.setText(bundle.getString(Constants.FROM).replaceAll("\\+", " "));
//            destination.setText(bundle.getString(Constants.DESTINATION).replaceAll("\\+", " "));
//            NumberFormat nf = NumberFormat.getCurrencyInstance();
//            price.setText(nf.format(bundle.getDouble(Constants.PRICE)));
//        }
//    }

}
