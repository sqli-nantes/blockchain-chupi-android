package com.sqli.blockchain.automotive;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter;

    ListView beaconListView;
    BeaconListAdapter beaconListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBluetoothScanner();
        initListView();
    }

    private void initListView() {

        beaconListView = (ListView) findViewById(R.id.listView);

        beaconListViewAdapter = new BeaconListAdapter();

        beaconListView.setAdapter(beaconListViewAdapter);

    }


    private void initBluetoothScanner(){

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        BluetoothLeScanner mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

        ScanCallback mScanCallback = new ScanCallback() {

            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                beaconListViewAdapter.add(result);
            }
        };

        ScanSettings.Builder mBuilder = new ScanSettings.Builder();
        mBuilder.setReportDelay(0);
        mBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBuilder.setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE);
        }

        mBluetoothLeScanner.startScan(mScanCallback);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
