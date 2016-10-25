package com.example.joel.automotive;

import android.app.Application;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.example.joel.automotive.contract.ChoupetteContract;
import com.sqli.blockchain.android_geth.EthereumApplication;

import ethereumjava.EthereumJava;
import ethereumjava.net.provider.AndroidIpcProvider;

/**
 * Created by root on 25/10/16.
 */

public class MyApplication extends EthereumApplication {


    public static final String PASSWORD = "toto";

    public String accountId;

    public EthereumJava ethereumjava;
    public ChoupetteContract choupetteContract;


    @Override
    public void onEthereumServiceReady() {

        String dir = ethereumService.getIpcFilePath();
        ethereumjava = new EthereumJava.Builder()
                .provider(new AndroidIpcProvider(dir))
                .build();

        accountId = ethereumjava.personal.newAccount("toto");
        Log.d("GETH",accountId);

        super.onEthereumServiceReady();

    }


    public void setContractAtAdress(String address){
        choupetteContract = (ChoupetteContract) ethereumjava.contract.withAbi(ChoupetteContract.class).at(address);
    }
}
