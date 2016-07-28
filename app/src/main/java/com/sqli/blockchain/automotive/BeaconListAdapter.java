package com.sqli.blockchain.automotive;

import android.bluetooth.le.ScanResult;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gunicolas on 28/07/16.
 */
public class BeaconListAdapter extends BaseAdapter {

    Map<String,ScanResult> beaconList;
    List<ScanResult> sortedBeaconList;

    public BeaconListAdapter() {
        beaconList = new HashMap<String,ScanResult>();
        sortedBeaconList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return sortedBeaconList.size();
    }

    @Override
    public ScanResult getItem(int i) {
        if( i < 0 || i > sortedBeaconList.size() ) return null;
        return sortedBeaconList.get(i);
    }

    @Override
    public long getItemId(int i) {
        if( i < 0 || i > sortedBeaconList.size() ) return -1;
        return sortedBeaconList.get(i).hashCode();
    }

    @Override
    public View getView(int i, View _view, ViewGroup viewGroup) {

        final View view;

        if( _view == null ){
           view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.beacon_listview_item,viewGroup,false);
        } else{
            view = _view;
        }

        ScanResult item = getItem(i);
        String address = item.getDevice().getAddress();
        int rssi = item.getRssi();
        int txpower = item.getScanRecord().getTxPowerLevel();
        double distance = Utils.beaconDistance(txpower,rssi);
        String state = Utils.getProximityFromDistance(distance);


        ((TextView)view.findViewById(R.id.address)).setText(address);
        ((TextView)view.findViewById(R.id.state)).setText(state);
        ((TextView)view.findViewById(R.id.rssi)).setText(String.valueOf(rssi));
        ((TextView)view.findViewById(R.id.txpower)).setText(String.valueOf(txpower));
        ((TextView)view.findViewById(R.id.distance)).setText(String.valueOf(distance).substring(0,5));

        return view;
    }

    public void add(ScanResult device){
        double distance = Utils.beaconDistance(device.getScanRecord().getTxPowerLevel(),device.getRssi());

        String deviceKey = device.getDevice().getAddress();

        if( distance < 3 ) beaconList.put(deviceKey, device);
        else beaconList.remove(deviceKey);

        sortedBeaconList = new ArrayList<>(beaconList.values());

        Collections.sort(sortedBeaconList, new Comparator<ScanResult>() {
            @Override
            public int compare(ScanResult scanResult, ScanResult t1) {
                double distance1 = Utils.beaconDistance(scanResult.getScanRecord().getTxPowerLevel(),scanResult.getRssi());
                double distance2 = Utils.beaconDistance(t1.getScanRecord().getTxPowerLevel(),t1.getRssi());

                return (int) -(distance1 - distance2);
            }
        });

        notifyDataSetChanged();
    }
}
