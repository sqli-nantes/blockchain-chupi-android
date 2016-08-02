package com.sqli.blockchain.automotive;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sqli.blockchain.automotive.ethereum.EthereumNodeManager;
import com.sqli.blockchain.automotive.ethereum.modules.Module;
import com.sqli.blockchain.automotive.ethereum.utils.Peer;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class MainActivity extends AppCompatActivity implements EthereumService.EthereumServiceInterface{

    static final String TAG = MainActivity.class.getSimpleName();

    AutomotiveApplication app;

    EthereumNodeManager ethereumNodeManager;

    Button nodeInfoButton;
    Button peersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonListener buttonListener = new ButtonListener();

        nodeInfoButton = (Button) findViewById(R.id.node_info);
        nodeInfoButton.setOnClickListener(buttonListener);

        peersButton = (Button) findViewById(R.id.peers);
        peersButton.setOnClickListener(buttonListener);

        app = (AutomotiveApplication) getApplication();

        if(  app.getEthereumService() == null ){
            enableButtons(false);
            app.registerClient(this);
        } else if( app.getEthereumService().getNodeManager() == null) {
            enableButtons(false);
            app.registerClient(this);
        } else{
            this.ethereumNodeManager = app.getEthereumService().getNodeManager();
            enableButtons(true);
        }
    }

    private void enableButtons(final boolean enable){
        nodeInfoButton.setEnabled(enable);
        peersButton.setEnabled(enable);
    }

    @Override
    protected void onDestroy() {
        app.getEthereumService().unregisterClient(this);
        super.onDestroy();
    }

    @Override
    public void onEthereumServiceReady(EthereumNodeManager _ethereumNodeManager) {
        this.ethereumNodeManager = _ethereumNodeManager;
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                enableButtons(true);
            }
        });
        this.app.getEthereumService().unregisterClient(this);
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view == nodeInfoButton) {
                ethereumNodeManager.admin.nodeInfo(new Module.ModuleCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean res) {

                    }

                    @Override
                    public void onFail(String errorMessage) {

                    }
                });
            } else if (view == peersButton) {
                ethereumNodeManager.admin.peers(new Module.ModuleCallback<List<Peer>>() {
                    @Override
                    public void onSuccess(List<Peer> res) {

                    }

                    @Override
                    public void onFail(String errorMessage) {

                    }
                });
            }
        }
    }
}
