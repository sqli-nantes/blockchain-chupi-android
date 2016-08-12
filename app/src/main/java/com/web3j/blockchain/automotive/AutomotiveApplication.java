package com.web3j.blockchain.automotive;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.web3j.blockchain.automotive.ethereum.EthereumNodeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gunicolas on 2/08/16.
 */
public class AutomotiveApplication extends Application implements EthereumService.EthereumServiceInterface {

    private static final String TAG = AutomotiveApplication.class.getSimpleName();

    EthereumService ethereumService;
    ServiceConnection ethereumServiceConnection;
    Intent ethereumServiceIntent;

    List<EthereumService.EthereumServiceInterface> ethereumServiceCallbacks;

    boolean ethereumServiceStarted;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("TOTO","create");

        onResume();

    }

    public void onResume(){

        ethereumServiceStarted = false;
        ethereumServiceCallbacks = new ArrayList<>();
        ethereumServiceIntent = new Intent(this,EthereumService.class);
        ethereumServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                EthereumService.LocalBinder binder = (EthereumService.LocalBinder) iBinder;
                ethereumService = binder.getServiceInstance();
                ethereumService.registerClient(AutomotiveApplication.this);
                ethereumService.checkGethReady();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {}
        };

        startService(ethereumServiceIntent);
        ethereumServiceStarted = true;

        bindService(ethereumServiceIntent, ethereumServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onTerminate() {
        stopServices();
        super.onTerminate();
    }

    private void stopServices(){
        if( ethereumServiceConnection != null )
            unbindService(ethereumServiceConnection);
        if( ethereumServiceIntent != null )
            stopService(ethereumServiceIntent);
        ethereumServiceStarted = false;
    }

    public void registerClient(EthereumService.EthereumServiceInterface client){
        this.ethereumServiceCallbacks.add(client);
    }
    public void unregisterClient(EthereumService.EthereumServiceInterface client){
        this.ethereumServiceCallbacks.remove(client);
    }

    public EthereumService getEthereumService() {
        return ethereumService;
    }

    public boolean isEthereumServiceStarted() {
        return ethereumServiceStarted;
    }

    @Override
    public void onEthereumServiceReady(EthereumNodeManager ethereumNodeManager) {
        for( EthereumService.EthereumServiceInterface client : this.ethereumServiceCallbacks ){
            client.onEthereumServiceReady(ethereumNodeManager);
        }
    }
}
