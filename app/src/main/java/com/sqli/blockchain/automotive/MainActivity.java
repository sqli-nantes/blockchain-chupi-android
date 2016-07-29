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

    Button button;
    Button button2;

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

        button = (Button) findViewById(R.id.node_info);
        button.setText("Node Info");
        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    nodeManager.admin.nodeInfo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        button2 = (Button) findViewById(R.id.peers);
        button2.setText("PEERS");
        button2.setEnabled(false);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    nodeManager.admin.peers();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    void getCoinbaseRPC(){

        new Thread(new Runnable() {
            @Override
            public void run() {
            try {
                URL url = new URL(EthereumService.GETH_RPC_ADDRESS+":"+EthereumService.GETH_RPC_PORT);
                byte[] req = "{\"jsonrpc\":\"2.0\",\"method\":\"eth_coinbase\",\"params\":[],\"id\":1}".getBytes(StandardCharsets.UTF_8);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Length",Integer.toString(req.length));
                try(DataOutputStream out = new DataOutputStream(connection.getOutputStream())){
                    out.write(req);
                }
                try{
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                    StringBuilder buffer = new StringBuilder();

                    String line;
                    while((line=in.readLine()) != null){
                        buffer.append(line);
                    }
                    final JSONObject reply = new JSONObject(buffer.toString());

                    Log.v(TAG,reply.toString());
                }
                finally {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        unbindService(ethereumServiceConnection);
        stopService(ethereumServiceIntent);
        super.onDestroy();
    }

    @Override
    public void onGethReady() {
        try {
            nodeManager = new EthereumNodeManager(EthereumService.dataDir+EthereumService.GETH_IPC_FILE);

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    button.setEnabled(true);
                    button2.setEnabled(true);
                }
            });


        } catch (IOException e) {
            Log.d(TAG,e.toString());
        }
    }
}
