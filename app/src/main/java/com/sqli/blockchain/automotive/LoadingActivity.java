package com.sqli.blockchain.automotive;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.ybq.android.spinkit.SpinKitView;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

/**
 * Created by gunicolas on 28/07/16.
 */
public class LoadingActivity extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter;
    BluetoothLeScanner mBluetoothLeScanner;
    ScanCallback mScanCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);

        startBluetoothScanner();
    }

    private void startBluetoothScanner(){

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

        mScanCallback = new ScanCallback() {

            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);

                int rssi = result.getRssi();
                int txPower = result.getScanRecord().getTxPowerLevel();

                double distance = Utils.beaconDistance(txPower,rssi);
                if( distance < 0.8 ){

                    mBluetoothLeScanner.stopScan(mScanCallback);

                    Intent intent = new Intent(LoadingActivity.this,DetectedCarActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
            }
        };

        ScanSettings.Builder mBuilder = new ScanSettings.Builder();
        mBuilder.setReportDelay(0);
        mBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);


        mBluetoothLeScanner.startScan(mScanCallback);
    }

    @Override
    public void onBackPressed() {}

    @Override
    protected void onDestroy() {
        mBluetoothLeScanner.stopScan(mScanCallback);

        super.onDestroy();
    }
}
