package com.sqli.blockchain.automotive;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gunicolas on 28/07/16.
 */
public class DetectedCarActivity extends AppCompatActivity {

    private static final String TAG = DetectedCarActivity.class.getSimpleName();
    private static final String PORT = ":8080";

    Button acceptCar;
    Button rejectCar;
    TextView carNameTextview;
    CircleImageView carImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detected_car_layout);

        carNameTextview = (TextView) findViewById(R.id.car_name);
        carImageview = (CircleImageView) findViewById(R.id.car_img);

        acceptCar = (Button) findViewById(R.id.accept_car);
        acceptCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(DetectedCarActivity.this)
                    .setTitle("Information")
                    .setMessage("Vous avez accepté la location de Choupette")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            goToMainActivity();
                        }
                    })
                    .show();
            }
        });

        rejectCar = (Button) findViewById(R.id.reject_car);
        rejectCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMainActivity();
            }
        });

        String url = getIntent().getStringExtra(LoadingActivity.INTENT_EXTRA_ADDRESS);
        url+=PORT;
        getCarInfos(url);
    }

    private void getCarInfos(String url) {

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest getInfosJSON = new JsonObjectRequest(Request.Method.GET, url,null,

            new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String carName = response.getString("name");
                        carNameTextview.setText(carName);
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
            }
        );

        queue.add(getInfosJSON);

        ImageRequest getImage = new ImageRequest(url+"/img", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                carImageview.setImageBitmap(response);
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
    public void onBackPressed() {}

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
