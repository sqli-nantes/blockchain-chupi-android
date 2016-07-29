package com.sqli.blockchain.automotive;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.github.ethereum.go_ethereum.cmd.Geth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by gunicolas on 26/07/16.
 */
public class EthereumService extends Service {

    public static final String TAG = "ETHEREUM_SERVICE";
    public static final String GETH_RPC_ADDRESS = "http://127.0.0.1";
    public static final int GETH_RPC_PORT = 3000;
    public static final int GETH_NETWORK_ID = 100;
    public static final String GETH_IPC_FILE = "/geth.ipc";
    public static String dataDir;

    private CallBacks callback;
    private final IBinder mBinder = new LocalBinder();

    private Thread gethThread;
    private Thread checkFileThread;




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        dataDir = getFilesDir().getAbsolutePath();

        final StringBuilder gethParams = new StringBuilder();
        /*
        gethParams.append("--ipcpath \""+getFilesDir().getAbsolutePath()+"/geth.ipc\"").append(" ");
        gethParams.append("--ipcdisable").append(" ");
        gethParams.append("--rpc").append(" ");
        gethParams.append("--rpcport "+GETH_RPC_PORT).append(" ");
        gethParams.append("--rpccorsdomain=*").append(" ");
        */
        gethParams.append("--fast").append(" ");
        gethParams.append("--lightkdf").append(" ");
        gethParams.append("--nodiscover").append(" ");
        gethParams.append("--networkid "+GETH_NETWORK_ID).append(" ");
        gethParams.append("--datadir=" + dataDir).append(" ");

        runGeth(gethParams.toString());

        return START_NOT_STICKY;
    }

    private void runGeth(final String gethParams) {
        gethThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Geth.run( gethParams );
            }
        });
        gethThread.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public interface CallBacks {
        void onGethReady();
    }
    public class LocalBinder extends Binder {
        public EthereumService getServiceInstance(){
            return EthereumService.this;
        }
    }
    public void registerClient(Activity activity){
        this.callback = (CallBacks) activity;
    }
    public void checkGethReady() {
        checkFileThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int attempts = 0;
                    while( !new File(dataDir+GETH_IPC_FILE).exists() ){
                        Log.d(TAG,"attenmpt : "+(++attempts));
                        Thread.sleep(500);
                    }
                    callback.onGethReady();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    checkFileThread.interrupt();
                }
            }
        });
        checkFileThread.start();
    }


    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile (File f) throws Exception {
        FileInputStream fin = new FileInputStream(f);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }


    @Override
    public void onDestroy() {
        gethThread.interrupt();
        checkFileThread.interrupt();
        super.onDestroy();
    }
}
