package com.sqli.blockchain.automotive;

import android.content.Intent;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
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


public class MainActivity extends AppCompatActivity {

    static final String TAG = "MAIN_ACTIVITY";

    Intent ethereumService;
    EthereumNodeManager nodeManager;

    Button button;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ethereumService = new Intent(this,EthereumService.class);
        ethereumService.putExtra("onReady",);
        startService(ethereumService);

        try {
            nodeManager = new EthereumNodeManager(getFilesDir().getAbsolutePath()+"/geth.ipc");
        } catch (IOException e) {
            Log.d(TAG,e.toString());
            // PASSE ICI
        }

        button = (Button) findViewById(R.id.button);
        button.setText("Node Info");
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

        button2 = (Button) findViewById(R.id.button2);
        button2.setText("PEERS");
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
        stopService(ethereumService);
        super.onDestroy();
    }
}
