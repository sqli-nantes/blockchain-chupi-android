package com.sqli.blockchain.automotive.ethereum;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;
import android.util.SparseArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Created by gunicolas on 27/07/16.
 */
public class RequestManager {

    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static int requestNumber = 1;

    final String ipcFilePath;
    LocalSocket socket;
    DataOutputStream out;

    BufferedReader in;

    SparseArray<Request> requestQueue;

    public RequestManager(String _ipcFilePath) throws IOException {
        this.ipcFilePath = _ipcFilePath;
        requestQueue = new SparseArray<>();
        createSocket();
    }

    private void createSocket() throws IOException {
        socket = new LocalSocket();

        socket.connect(new LocalSocketAddress(ipcFilePath, LocalSocketAddress.Namespace.FILESYSTEM));
        out = new DataOutputStream(socket.getOutputStream());

        in = new BufferedReader(new InputStreamReader(socket.getInputStream(),CHARSET.name()));
        listenSocket();
    }

    private void listenSocket(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        StringBuilder buffer = new StringBuilder();
                        String line;
                        while (in.ready() && (line = in.readLine()) != null) {
                            buffer.append(line);
                            Log.d(RequestManager.class.getSimpleName(), line);
                        }

                        /*try {
                            JSONObject responseObject = new JSONObject(buffer.toString());
                            responseObject.get
                        }
                        catch( JSONException jsonException){
                            Log.d(RequestManager.class.getSimpleName(),"Error parsing JSON response : "+buffer.toString());
                        }*/

                        Thread.sleep(500);
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendAsync(Request m) throws IOException {

        String stringRequest =  "{" +
                                    "\"jsonrpc\":\"2.0\"," +
                                    "\"method\":\""+ m.getName() +"\"," +
                                    "\"params\":"+ Arrays.deepToString(m.getParameters())+"," +
                                    "\"id\":"+requestNumber+"" +
                                "}";

        byte[] req = stringRequest.getBytes(CHARSET);

        out.write(req);

        requestQueue.put(requestNumber,m);

        requestNumber++;
    }

    private void stop(){
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
