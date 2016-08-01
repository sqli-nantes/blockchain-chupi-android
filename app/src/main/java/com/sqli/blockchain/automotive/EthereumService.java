package com.sqli.blockchain.automotive;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.github.ethereum.go_ethereum.cmd.Geth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by gunicolas on 26/07/16.
 */
public class EthereumService extends Service {

    public static final String TAG = "ETHEREUM_SERVICE";
    //public static final String GETH_RPC_ADDRESS = "http://127.0.0.1";
    //public static final int GETH_RPC_PORT = 3000;
    public static final int GETH_NETWORK_ID = 100;
    public static final String GETH_IPC_FILE = "/geth.ipc";
    public static final String GETH_GENESIS_FILE = "/genesis.json";

    public static String dataDir;

    private CallBacks callback;
    private final IBinder mBinder = new LocalBinder();

    private Thread gethThread;
    private Thread checkFileThread;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        dataDir = getFilesDir().getAbsolutePath();

        try {
            saveGenesisOnStorage();

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
            gethParams.append("--genesis=" + dataDir+GETH_GENESIS_FILE).append(" ");

            runGeth(gethParams.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return START_NOT_STICKY;
    }

    private void saveGenesisOnStorage() throws Exception {
        AssetManager asset = getBaseContext().getAssets();
        InputStream in = asset.open("genesis.json");
        new File(dataDir+GETH_GENESIS_FILE).createNewFile();
        OutputStream out = new FileOutputStream(dataDir+GETH_GENESIS_FILE);
        byte[] buffer = new byte[1024];
        int read;
        while((read=in.read(buffer)) != -1){
            out.write(buffer,0,read);
        }
        in.close();
        in = null;
        out.flush();
        out.close();
        out = null;

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

    @Override
    public void onDestroy() {
        gethThread.interrupt();
        checkFileThread.interrupt();
        super.onDestroy();
    }

}
