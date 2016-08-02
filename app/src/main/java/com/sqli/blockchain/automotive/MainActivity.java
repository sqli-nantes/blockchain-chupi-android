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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity implements EthereumService.EthereumServiceInterface{

    static final String TAG = MainActivity.class.getSimpleName();

    AutomotiveApplication app;

    EthereumNodeManager ethereumNodeManager;

    Button nodeInfoButton;
    Button peersButton;
    Button addPeerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonListener buttonListener = new ButtonListener();

        nodeInfoButton = (Button) findViewById(R.id.node_info);
        nodeInfoButton.setOnClickListener(buttonListener);

        peersButton = (Button) findViewById(R.id.peers);
        peersButton.setOnClickListener(buttonListener);

        addPeerButton = (Button) findViewById(R.id.add_peer);
        addPeerButton.setOnClickListener(buttonListener);


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
        addPeerButton.setEnabled(enable);
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
            try {
                if (view == nodeInfoButton) {
                    ethereumNodeManager.admin.nodeInfo();
                } else if (view == peersButton) {
                    ethereumNodeManager.admin.peers();
                } else if (view == addPeerButton) {
                    ethereumNodeManager.admin.addPeer("enode://fdb1dbd24161585d557d1edf6f268a878dd53a9fac56417fe7d3a37518530cfed0d6dec620c68491bcf9e947190081918eebe3bced509ee9ed78ef1355f296a8@10.33.44.222:30303");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
