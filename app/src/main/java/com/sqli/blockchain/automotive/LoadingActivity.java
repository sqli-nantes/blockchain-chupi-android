package com.sqli.blockchain.automotive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.utils.UrlBeaconUrlCompressor;

import java.util.Collection;

/**
 * Created by gunicolas on 28/07/16.
 */
public class LoadingActivity extends AppCompatActivity implements BeaconConsumer, RangeNotifier {

    public static final String INTENT_EXTRA_ADDRESS = "ADDRESS";

    private static final double DISTANCE = 0.005;
    private static final String DEVICE_NAME = "choupette";


    BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);

        startBluetoothScanner();
    }

    private void startBluetoothScanner(){

        beaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
        beaconManager.setForegroundScanPeriod(100L); //1 period scan duration
        beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-20v"));
        beaconManager.bind(this);

    }

    @Override
    public void onBeaconServiceConnect() {

        Region region = new Region("all-beacons-region",null,null,null);
        try{
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        beaconManager.addRangeNotifier(this);
    }


    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
        for(final Beacon b : collection){

            if( b.getBluetoothName().equals(DEVICE_NAME) && b.getDistance() < DISTANCE ) {

                beaconManager.unbind(this);

                final String url = UrlBeaconUrlCompressor.uncompress(b.getId1().toByteArray());

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(200);

                Intent intent = new Intent(LoadingActivity.this,DetectedCarActivity.class);
                intent.putExtra(INTENT_EXTRA_ADDRESS,url);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        }
    }

    @Override
    public void onBackPressed() {}

    @Override
    protected void onPause() {
        beaconManager.unbind(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        beaconManager.unbind(this);
        super.onDestroy();
    }
}
