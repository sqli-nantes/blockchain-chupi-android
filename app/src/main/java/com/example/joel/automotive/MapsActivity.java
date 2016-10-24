package com.example.joel.automotive;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private Marker marker;
    private LatLng here;
    private String hereString;
    private EditText edtSeach;
    private FloatingActionButton fab;
    private List<Address> addresses;
    private Polyline line;
    private Car car;
    private Address destinationAddress;
    private Address hereAddress;
    private double amount;
    private String destination;
    private boolean isSearchOpened = false;
    private PolylineOptions pOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fab = (FloatingActionButton) findViewById(R.id.btn_ok);
        car = (Car) getIntent().getExtras().getSerializable(Constants.CAR);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this,SummaryActivity.class);
                intent.putExtra(Constants.CAR,car);
                intent.putExtra(Constants.FROM,hereString);
                intent.putExtra(Constants.DESTINATION,destination);
                intent.putExtra(Constants.PRICE,amount);
                startActivity(intent);
            }
        });

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_home) {
            Intent intent = new Intent(MapsActivity.this,MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    protected void handleMenuSearch(){
        edtSeach = (EditText)findViewById(R.id.edtSearch); //the text editor

        //this is a listener to do a search when the user clicks on search button
        edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                Geocoder geo = new Geocoder(getApplicationContext());
                try {
                    addresses = geo.getFromLocationName(edtSeach.getText().toString(), 1);
                    if (addresses.toArray().length>0){
                        setMark(new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude()));
                    }
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

                    final View mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();
                    if (mapView.getViewTreeObserver().isAlive()) {
                        mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @SuppressLint("NewApi")
                            @Override
                            public void onGlobalLayout() {
                            LatLngBounds.Builder bld = new LatLngBounds.Builder();
                            bld.include(here);
                            for (int i = 0; i < addresses.size(); i++) {
                                bld.include(new LatLng(addresses.get(i).getLatitude(),addresses.get(i).getLongitude()));
                            }
                            LatLngBounds bounds = bld.build();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 70));
                            mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false; // pass on to other listeners.
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        handleMenuSearch();

        camera();

        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                // Flip the values of the r, g and b components of the polyline's color.
                int strokeColor = Color.DKGRAY;
                polyline.setColor(strokeColor);
            }
        });

        mMap.addMarker(new MarkerOptions().position(here).title("Vous êtes ici!"));
        mMap.setOnMapClickListener(this);
    }

    private void camera() {

        here = null;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

        if (location != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));
            here = new LatLng(location.getLatitude(), location.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(here)               // Sets the center of the map to location user
                    .zoom(15)                   // Sets the zoom
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        setMark(latLng);
    }

    private void setMark(LatLng latLng){

        if (marker!=null){
            marker.remove();
            line.remove();
        }

        Geocoder geocoder = new Geocoder(getApplicationContext());

        try {
            destinationAddress = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1).get(0);
            hereAddress = geocoder.getFromLocation(here.latitude,here.longitude,1).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (destinationAddress != null) {
            destination = "";
            for (int i = 0; i < destinationAddress.getMaxAddressLineIndex(); i++) {
                destination += destinationAddress.getAddressLine(i);
                if (i<destinationAddress.getMaxAddressLineIndex()-1){
                    destination += " ";
                }
            }
            edtSeach.setText(destination);
            destination = destination.replaceAll("\\s+","+");
        }
        if (hereAddress != null) {
            hereString = "";
            for (int i = 0; i < hereAddress.getMaxAddressLineIndex(); i++) {
                hereString += hereAddress.getAddressLine(i);
                if (i<hereAddress.getMaxAddressLineIndex()-1){
                    hereString += " ";
                }
            }
            hereString = hereString.replaceAll("\\s+","+");
        }
        marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Destination").draggable(true));
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        findViewById(R.id.fl_button).setVisibility(View.VISIBLE);
        amount = 0;
        List<LatLng> latLngs = new ArrayList();
        try {
            latLngs = Utils.getWayPoints(hereString,destination);
            latLngs = Utils.SnapToRoad(latLngs);
            amount = Utils.getDistance(hereString,destination);
            Toast.makeText(getApplicationContext(), destination + "\nTemps: " + Utils.getDuration(hereString,destination), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try{
            pOptions = new PolylineOptions();
            pOptions.add(here);
            for (LatLng l:latLngs) {
                pOptions.add(l);
            }
            pOptions.add(new LatLng(destinationAddress.getLatitude(),destinationAddress.getLongitude()));
            pOptions.color(Color.BLUE).width(6).geodesic(true).clickable(true);
            //here is where it will draw the polyline in your map
            line = mMap.addPolyline(pOptions);
        }catch(NullPointerException e){
            Log.e("Error", "NullPointerException onPostExecute: " + e.toString());
        }catch (Exception e2) {
            Log.e("Error", "Exception onPostExecute: " + e2.toString());
        }
    }
}
