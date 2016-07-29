package com.sqli.blockchain.automotive;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by gunicolas on 28/07/16.
 */
public class DetectedCarActivity extends AppCompatActivity {

    Button acceptCar;
    Button rejectCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detected_car_layout);

        acceptCar = (Button) findViewById(R.id.accept_car);
        acceptCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(DetectedCarActivity.this)
                    .setTitle("Information")
                    .setMessage("Vous avez accepté la location de Choupette")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            goToMainActivity(true);
                        }
                    })
                    .show();
            }
        });

        rejectCar = (Button) findViewById(R.id.reject_car);
        rejectCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMainActivity(false);
            }
        });

    }

    private void goToMainActivity(boolean accepted){
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    @Override
    public void onBackPressed() {}

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
