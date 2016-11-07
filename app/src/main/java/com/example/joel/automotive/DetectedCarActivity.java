package com.example.joel.automotive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gunicolas on 28/07/16.
 * Activité permettant le choix de la voiture à louer
 *
 */
public class DetectedCarActivity extends AppCompatActivity{

    private static final String TAG = DetectedCarActivity.class.getSimpleName();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private Car car;
    private List<Car> cars;
    private Bundle args;
    public int nbCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choupette);
        args = new Bundle();
        car = new Car();

        CoordinatorLayout cLayout = (CoordinatorLayout)findViewById(R.id.main_content);
        //cLayout.getBackground().setAlpha(120);
        ArrayList<String> urls = getIntent().getStringArrayListExtra(Constants.URLSCAN);
        nbCars = urls.size();
        if (nbCars>0) {
            for (String url : urls) {
//                url += Constants.PORT;
                getCarInfos(url);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.removeItem(R.id.action_back);
        menu.removeItem(R.id.action_refresh);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            goToMainActivity();
        }

        return super.onOptionsItemSelected(item);
    }


    private void getCarInfos(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);

//Ne prends en compte que la voiture "Choupette"
//        JsonObjectRequest getInfosJSON = new JsonObjectRequest(Request.Method.GET, url+"/toto.json",null,new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    car.setName(response.getString("name"));
//                    car.setManufacturer(response.getString("manufacturer"));
//                    car.setModel(response.getString("model"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        },
//        new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d(TAG,error.toString());
//            }
//        });

//Passage en dur de l'adresse IP pour faire les tests sans lecture du QRcode
        JsonObjectRequest getInfosJSON = new JsonObjectRequest(Request.Method.GET, "http://10.33.44.57:8080/toto.json",null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    car.setName(response.getString("name"));
                    car.setManufacturer(response.getString("manufacturer"));
                    car.setModel(response.getString("model"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,error.toString());
                    }
                });

        queue.add(getInfosJSON);

        ImageRequest getImage = new ImageRequest(url+"/img.jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                car.setImage(response);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout,new ChoupetteFragment().newInstance(car)).commit();
            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,error.toString());
            }
        });

        queue.add(getImage);
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(DetectedCarActivity.this, R.string.nepastoucher,
                Toast.LENGTH_SHORT).show();
        return;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}