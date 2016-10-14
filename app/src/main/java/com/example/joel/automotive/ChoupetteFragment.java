package com.example.joel.automotive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by joel on 03/08/16.
 */
public class ChoupetteFragment extends Fragment {
    private TextView car_name;
    private ImageView car_img;
    private LinearLayout infos;
    private TextView car_manufacturer;
    private TextView car_model;
    private Button btn_ok;
    private FloatingActionButton fb_more;
    private Car car;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choupette, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_ok = (Button)view.findViewById(R.id.btn_ok);
        car_name = (TextView)view.findViewById(R.id.car_name);
        car_img = (ImageView) view.findViewById(R.id.car_img);
        car_manufacturer = (TextView) view.findViewById(R.id.car_manufacturer);
        car_model = (TextView) view.findViewById(R.id.car_model);
        fb_more = (FloatingActionButton)view.findViewById(R.id.btn_more);
        infos = (LinearLayout)view.findViewById(R.id.infos);

        if (getArguments() != null) {
            Bundle args = getArguments();
            if (args.containsKey(Constants.CAR)) {
                car = (Car) args.getSerializable(Constants.CAR);
                setName(car.getName());
                setManufacturer(car.getManufacturer());
                setModel(car.getModel());
            }
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SelectedDestinationActivity.class);
                intent.putExtra(Constants.CAR,car);
                startActivity(intent);
            }
        });

        fb_more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (infos.getVisibility()==View.GONE){
                    infos.setVisibility(View.VISIBLE);
                    fb_more.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_remove));
                }else{
                    infos.setVisibility(View.GONE);
                    fb_more.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_add));
                }
            }
        });
    }

    public static ChoupetteFragment newInstance(Car car) {
        ChoupetteFragment fragment = new ChoupetteFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.CAR, car);
        fragment.setArguments(args);
        return fragment;
    }

    public void setName(String name) {
        car_name.setText(name);
    }

    public void setImage(Bitmap image) {
        car_img.setImageBitmap(image);
    }

    public void setManufacturer(String manufacturer){
        car_manufacturer.setText(manufacturer);
    }

    public void setModel(String model){
        car_model.setText(model);
    }
}
