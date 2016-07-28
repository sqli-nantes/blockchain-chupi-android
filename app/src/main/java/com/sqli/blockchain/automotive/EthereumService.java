package com.sqli.blockchain.automotive;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.github.ethereum.go_ethereum.cmd.Geth;

import java.util.Arrays;

/**
 * Created by gunicolas on 26/07/16.
 */
public class EthereumService extends IntentService {

    public static final String TAG = "ETHEREUM_SERVICE";
    public static final String GETH_RPC_ADDRESS = "http://127.0.0.1";
    public static final int GETH_RPC_PORT = 3000;
    static final int GETH_NETWORK_ID = 100;

    public EthereumService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG,"Service Started");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        StringBuilder gethParams = new StringBuilder();
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
        gethParams.append("--datadir=" + getFilesDir().getAbsolutePath()).append(" ");

        Geth.run( gethParams.toString());

        intent.getBundleExtra("onReady");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onDestroy() {
        Thread.currentThread().interrupt();
        try {
            Thread.currentThread().join();
            Log.v(TAG,"Ethereum Service destroyed");
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
        super.onDestroy();
    }
}
