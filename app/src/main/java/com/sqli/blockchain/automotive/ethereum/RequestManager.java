package com.sqli.blockchain.automotive.ethereum;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;
import android.util.SparseArray;

import com.sqli.blockchain.automotive.EthereumService;

import org.json.JSONException;
import org.json.JSONObject;

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
    private static final String TAG = RequestManager.class.getSimpleName();
    private static int requestNumber = 1;

    final String ipcFilePath;
    LocalSocket socket;
    DataOutputStream out;
    Thread listeningThread;

    BufferedReader in;

    SparseArray<Request> requestQueue;

    public RequestManager(String _ipcFilePath) throws IOException {
        this.ipcFilePath = _ipcFilePath;
        requestQueue = new SparseArray<>();
        createSocket();
    }

    private void createSocket() throws IOException {
        socket = new LocalSocket();
        if( !socket.isConnected() ) {
            socket.connect(new LocalSocketAddress(ipcFilePath, LocalSocketAddress.Namespace.FILESYSTEM));
        } else{
            Log.d(EthereumService.TAG,socket.getLocalSocketAddress().getName());
        }
        out = new DataOutputStream(socket.getOutputStream());

        in = new BufferedReader(new InputStreamReader(socket.getInputStream(),CHARSET.name()));
        listenSocket();
    }

    private void listenSocket(){
        listeningThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        StringBuilder buffer = new StringBuilder();
                        String line;
                        while (in.ready() && (line = in.readLine()) != null) {
                            buffer.append(line);

                            Log.d(TAG,line);

                            Response res = formatResponse(line);
                            dispatchRequestCallback(res);
                        }
                        Thread.sleep(500);
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        listeningThread.start();
    }

    private void dispatchRequestCallback(Response res) {
        int reqId = res.getId();
        Request req = requestQueue.get(reqId);
        if( req.isAsync() ){
            AsyncRequest asyncReq = (AsyncRequest) req;
            if( res instanceof SuccessfulResponse){
                asyncReq.onSuccess((SuccessfulResponse) res);
            } else if( res instanceof ErrorResponse){
                asyncReq.onFail((ErrorResponse) res);
            }
        } else { //It's a synchronous request
            //TODO
        }
        requestQueue.remove(reqId);
    }

    private Response formatResponse(String line) throws JSONException {
        JSONObject obj = new JSONObject(line);
        if( obj.has("result") ){
            return new SuccessfulResponse(obj);
        } else if( obj.has("error") ){
            return new ErrorResponse(obj);
        } else{
            throw new JSONException("unknown response type : "+line);
        }
    }

    public void sendAsync(Request m) throws IOException {

        String stringRequest =  "{" +
                                    "\"jsonrpc\":\"2.0\"," +
                                    "\"method\":\""+ m.getName() +"\"," +
                                    "\"params\":"+ Arrays.deepToString(m.getParameters())+"," +
                                    "\"id\":"+requestNumber+"" +
                                "}";

        Log.d(RequestManager.class.getSimpleName(),stringRequest);

        byte[] req = stringRequest.getBytes(CHARSET);

        out.write(req);

        requestQueue.put(requestNumber,m);

        requestNumber++;
    }

    public void stop() throws IOException {
        if( this.socket != null ) {
            this.socket.close();
        }
        if( this.listeningThread != null ){
            this.listeningThread.interrupt();
        }
    }



}
