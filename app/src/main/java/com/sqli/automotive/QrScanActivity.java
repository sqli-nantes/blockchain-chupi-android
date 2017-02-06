package com.sqli.automotive;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sqli.automotive.R;

import java.util.ArrayList;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by joel on 08/08/16.
 * Scan QR code
 */
public class QrScanActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1000;
    private ZBarScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view

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
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, view, menuInfo);
        menu.setHeaderTitle("Cette action va arrêter la transaction");
        menu.add(0, view.getId(), 0, "Retour à l'acceuil");
        menu.add(0, view.getId(), 0, "Annuler");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_home) {
            Intent intent = new Intent(QrScanActivity.this,MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)) {

                //TODO
                Log.d("CHUPI","shouldShowRequestPermissionRationale");
            } else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }

        } else {
            mScannerView.startCamera();          // Start camera on resume
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        ArrayList<String> urls = new ArrayList();
        String url = rawResult.getContents();
        url = "http://" + url;
        urls.add(url);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(200);
        Intent intent = new Intent(mScannerView.getContext(),DetectedCarActivity.class);
        intent.putStringArrayListExtra(Constants.URLSCAN,urls);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(QrScanActivity.this, R.string.nepastoucher,
                Toast.LENGTH_SHORT).show();
        return;

    }
}
