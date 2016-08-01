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


public class MainActivity extends AppCompatActivity implements EthereumService.CallBacks{

    static final String TAG = "MAIN_ACTIVITY";

    Intent ethereumServiceIntent;
    EthereumService ethereumService;
    ServiceConnection ethereumServiceConnection;

    EthereumNodeManager nodeManager;

    Button nodeInfoButton;
    Button peersButton;
    Button addPeerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ethereumServiceIntent = new Intent(this,EthereumService.class);
        ethereumServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                EthereumService.LocalBinder binder = (EthereumService.LocalBinder) iBinder;
                ethereumService = binder.getServiceInstance();
                ethereumService.registerClient(MainActivity.this);
                ethereumService.checkGethReady();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {}
        };

        startService(ethereumServiceIntent);
        bindService(ethereumServiceIntent, ethereumServiceConnection,Context.BIND_AUTO_CREATE);

        ButtonListener buttonListener = new ButtonListener();

        nodeInfoButton = (Button) findViewById(R.id.node_info);
        nodeInfoButton.setEnabled(false);
        nodeInfoButton.setOnClickListener(buttonListener);

        peersButton = (Button) findViewById(R.id.peers);
        peersButton.setEnabled(false);
        peersButton.setOnClickListener(buttonListener);

        addPeerButton = (Button) findViewById(R.id.add_peer);
        addPeerButton.setEnabled(false);
        addPeerButton.setOnClickListener(buttonListener);


    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            try {
                if (view == nodeInfoButton) {
                    nodeManager.admin.nodeInfo();
                } else if (view == peersButton) {
                    nodeManager.admin.peers();
                } else if (view == addPeerButton) {
                    nodeManager.admin.addPeer("enode://b38b347d809fd10ebb7aba2f60091c89efdd18b12d093731ca374b0a404253646a94d326c472cbe80c713867a86eaca45b4a40f10724640cfead53ead05ae5a2@[::]:30303");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopEthereum(){
        try {
            unbindService(ethereumServiceConnection);
            stopService(ethereumServiceIntent);
            nodeManager.stop();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        stopEthereum();
        super.onDestroy();
    }


    @Override
    protected void onStop() {
        stopEthereum();
        super.onStop();
    }

    @Override
    public void onGethReady() {
        try {
            nodeManager = new EthereumNodeManager(EthereumService.dataDir+EthereumService.GETH_IPC_FILE);

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    nodeInfoButton.setEnabled(true);
                    peersButton.setEnabled(true);
                    addPeerButton.setEnabled(true);
                }
            });


        } catch (IOException e) {
            Log.d(TAG,e.toString());
            stopEthereum();
            finish();
        }
    }
}
