package com.example.joel.automotive;

import android.os.StrictMode;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    public static List<LatLng> getWayPoints(String from,String to) throws IOException, JSONException {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json?");
        urlString.append("origin=");//from
        urlString.append(from);
        urlString.append("&destination=");//to
        urlString.append(to);
        urlString.append("&waypoints=optimize:true|");
        urlString.append(to);
        urlString.append("&key=AIzaSyAhFnO6y_PeNtARhf212iS1kri_azSv0Fk&sensor=true");

        JSONObject object = Connection(urlString.toString());


        JSONArray routesArray = object.getJSONArray("routes");
        JSONObject routes = routesArray.getJSONObject(0);

        JSONArray legsArray = routes.getJSONArray("legs"); // Get all legs for each row as an array
        JSONObject legs = legsArray.getJSONObject(0);

        JSONArray stepsArray = legs.getJSONArray("steps"); // Get all steps for each stepsArray as an array

        List<LatLng> latlngs= new ArrayList();

        JSONObject firstStep = stepsArray.getJSONObject(0).getJSONObject("start_location");
        latlngs.add(new LatLng(firstStep.getDouble("lat"),firstStep.getDouble("lng")));

        JSONObject end_location;

        for (int i=0 ; i<stepsArray.length() ; i++) {
            end_location = stepsArray.getJSONObject(i).getJSONObject("end_location");
            latlngs.add(new LatLng(end_location.getDouble("lat"),end_location.getDouble("lng")));
        }

        return latlngs;
    }


    public static List<LatLng> SnapToRoad(List<LatLng> ls) throws IOException, JSONException {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://roads.googleapis.com/v1/snapToRoads?path=");
        for (int i=0; i<ls.size(); i++){
            urlString.append(ls.get(i).latitude);
            urlString.append(",");
            urlString.append(ls.get(i).longitude);
            urlString.append(i<ls.size()-1?"|":"");
        }
        urlString.append("&interpolate=true&key=AIzaSyAhFnO6y_PeNtARhf212iS1kri_azSv0Fk");

        JSONObject object = Connection(urlString.toString());


        JSONArray snappedPointsArray = object.getJSONArray("snappedPoints");
        List<LatLng> latlngs= new ArrayList();

        for (int i=0 ; i<snappedPointsArray.length() ; i++) {
            JSONObject snappedPoints = snappedPointsArray.getJSONObject(i);
            JSONObject location = snappedPoints.getJSONObject("location");
            latlngs.add(new LatLng(location.getDouble("latitude"),location.getDouble("longitude")));
        }

        return latlngs;
    }


    private static JSONObject CalculationByDistance(String from, String to) throws IOException, JSONException {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/distancematrix/json?");
        urlString.append("origins=");//from
        urlString.append(from);
        urlString.append("&destinations=");//to
        urlString.append(to);
        urlString.append("&mode=driving&key=AIzaSyAhFnO6y_PeNtARhf212iS1kri_azSv0Fk&sensor=true");
        Log.d("xxx","URL="+urlString.toString());

        JSONObject object = Connection(urlString.toString());

        JSONArray rowsArray = object.getJSONArray("rows");
        JSONObject rows = rowsArray.getJSONObject(0);
        JSONArray elementsArray = rows.getJSONArray("elements"); // Get all elements for each row as an array
        JSONObject newDisTimeOb = elementsArray.getJSONObject(0);

        return newDisTimeOb;
    }

    public static double getDistance(String from, String to) throws IOException, JSONException {
        JSONObject distOb = CalculationByDistance(from,to).getJSONObject("distance");
        double iDistance = distOb.getDouble("value") / 1000;
        return iDistance;
    }

    public static String getDuration(String from, String to) throws IOException, JSONException {
        JSONObject durOb = CalculationByDistance(from,to).getJSONObject("duration");
        int hours = (int) (durOb.getDouble("value") / 3600);
        int minutes = (int) (durOb.getDouble("value") % 3600 / 60);
        int secondes = (int) (durOb.getDouble("value") % 60);
        String iDuration = (hours>0?hours + "h":"");
        iDuration+= (hours>0 || minutes>0)? minutes + "min":"";
        iDuration+= secondes + "s";
        return iDuration;
    }


    private static JSONObject Connection(String urlString) throws IOException, JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        // get the JSON And parse it to get the directions data.
        HttpURLConnection urlConnection;
        URL url;

        url = new URL(urlString);
        urlConnection=(HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.connect();

        InputStream inStream = urlConnection.getInputStream();
        BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));

        String temp, response = "";
        while((temp = bReader.readLine()) != null){
            //Parse data
            response += temp;
        }

        //Close the reader, stream & connection
        bReader.close();
        inStream.close();
        urlConnection.disconnect();


        //Sortout JSONresponse
        JSONObject object = (JSONObject) new JSONTokener(response).nextValue();

        return object;
    }
}
