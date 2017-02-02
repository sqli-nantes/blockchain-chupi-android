package com.sqli.automotive;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.sqli.automotive.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.utils.UrlBeaconUrlCompressor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gunicolas on 28/07/16.
 */
public class LoadingActivity extends AppCompatActivity implements BeaconConsumer, RangeNotifier {

    private Timer timer;

    BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        if (id == R.id.action_refresh) {
            timer.cancel();
            finish();
            startActivity(getIntent());
        }
        if (id == R.id.action_home) {
            timer.cancel();
            Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        startBluetoothScanner();
        timer = new Timer();
        timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        LoadingActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                new AlertDialog.Builder(LoadingActivity.this)
                                        .setTitle("Information")
                                        .setMessage("Aucune voiture de disponible dans les environs")
                                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                        }).show();
                            }
                        });
                    }
                },100000);
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
        ArrayList<String> urls = new ArrayList();
        for(final Beacon b : collection){

            if( b.getBluetoothName().equals(Constants.DEVICE_NAME) && b.getDistance() < Constants.DISTANCE ) {

                beaconManager.unbind(this);

                final String url = UrlBeaconUrlCompressor.uncompress(b.getId1().toByteArray());

                urls.add(url);
            }
        }
        if (urls.size()>0){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(200);
            Intent intent = new Intent(LoadingActivity.this,DetectedCarActivity.class);
            intent.putStringArrayListExtra(Constants.URLSCAN,  urls);
            startActivity(intent);
            overridePendingTransition(0,0);
        }
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        timer.cancel();
        beaconManager.unbind(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        beaconManager.unbind(this);
        super.onDestroy();
    }
}
