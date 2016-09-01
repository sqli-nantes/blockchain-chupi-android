package com.web3j.blockchain.automotive;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import web3j.Web3J;
import web3j.net.provider.AndroidIpcProvider;


/**
 * Created by gunicolas on 22/08/16.
 */
public abstract class EthereumActivity extends AppCompatActivity implements EthereumService.EthereumServiceInterface {

    static final String TAG = EthereumActivity.class.getSimpleName();

    EthereumService ethereumService;
    ServiceConnection ethereumServiceConnection;
    Intent ethereumServiceIntent;

    protected Web3J web3J;


    @Override
    protected void onResume() {
        super.onResume();

        ethereumServiceIntent = new Intent(this,EthereumService.class);
        ethereumServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                EthereumService.LocalBinder binder = (EthereumService.LocalBinder) iBinder;
                ethereumService = binder.getServiceInstance();
                ethereumService.registerClient(EthereumActivity.this);
                ethereumService.checkGethReady();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };

        startService(ethereumServiceIntent);
        bindService(ethereumServiceIntent,ethereumServiceConnection,BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        ethereumService.stop();
        ethereumService.stopSelf();
        unbindService(ethereumServiceConnection);
        stopService(ethereumServiceIntent);

        super.onStop();
    }

    @Override
    public void onEthereumServiceReady() {
        ethereumService.unregisterClient(this);
        String dir = ethereumService.getIpcFilePath();
        AndroidIpcProvider provider = new AndroidIpcProvider(dir);
        provider.listen().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                Log.e(TAG,throwable.getMessage());
            }
        });
        Web3J.Builder builder = new Web3J.Builder();
        builder.provider(provider);
        web3J = builder.build();
    }




}
