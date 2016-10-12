package com.example.joel.automotive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.location.Address;
import android.support.design.widget.FloatingActionButton;


public class RoadActivity extends AppCompatActivity {
// Pour passer outre la map et proposer 5 trajets
// Doit se placer entre DetectedCarActivity et Summary Activity
    private Car car;
    private Address destinationAddress;
    private Address hereAddress;
    private String destination;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road);

        fab = (FloatingActionButton) findViewById(R.id.btn_ok);
        car = (Car) getIntent().getExtras().getSerializable(Constants.CAR);

        dest = (Address) destinationAddress;
        ori = (Address) destinationAddress;
    }
}
