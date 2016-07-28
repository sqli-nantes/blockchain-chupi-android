package com.sqli.blockchain.automotive;

import android.util.Log;

/**
 * Created by gunicolas on 28/07/16.
 */
public abstract class Utils {


    /*
        AltBeacon algorithm to compute distance to beacon.
        Based on the signal intensity and RSSI.
     */
    public static double beaconDistance(int txPower, double rssi){
        if( rssi == 0) return -1;

        double ratio = rssi*1.0/txPower;

        if( ratio < 1.0 ){
            return Math.pow(ratio,10) * Math.pow(10,75); // from calibration
        }

        double accuracy = (0.89976)*Math.pow(ratio,7.7095) + 0.111;
        return accuracy;

    }

    public static String getProximityFromDistance(double distance){
        if( distance == -1 ) return "unknown";
        else if( distance < 1 ) return "Immediate";
        else if( distance < 3 ) return "Near";
        else return "Far";
    }

}
