package com.sqli.blockchain.automotive;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.github.ethereum.go_ethereum.cmd.Geth;
import com.sqli.blockchain.automotive.ethereum.EthereumNodeManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gunicolas on 26/07/16.
 */
public class EthereumService extends Service {

    public static final String TAG = "ETHEREUM_SERVICE";
    public static final int GETH_NETWORK_ID = 100;
    public static final String GETH_IPC_FILE = "/geth.ipc";
    public static final String GETH_GENESIS_FILE = "genesis.json";
    public static final String GETH_BOOTNODE_FILE = "static-nodes.json";

    public static String dataDir;

    private List<EthereumServiceInterface> callbacks;
    private final IBinder mBinder = new LocalBinder();

    private EthereumNodeManager nodeManager;

    private Thread gethThread;
    private Thread checkFileThread;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        dataDir = getFilesDir().getAbsolutePath();
        callbacks = new ArrayList<>();

        try {

            saveAssetOnStorage(GETH_GENESIS_FILE);
            saveAssetOnStorage(GETH_BOOTNODE_FILE);

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
            gethParams.append("--genesis=" + dataDir + "/" + GETH_GENESIS_FILE).append(" ");

            runGeth(gethParams.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return START_NOT_STICKY;
    }

    private void saveAssetOnStorage(String assetFilename) throws Exception {
        AssetManager asset = getBaseContext().getAssets();
        InputStream in = asset.open(assetFilename);
        String filePath = dataDir+"/"+assetFilename;
        new File(filePath).createNewFile();
        OutputStream out = new FileOutputStream(filePath);
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

    public interface EthereumServiceInterface {
        void onEthereumServiceReady(EthereumNodeManager ethereumNodeManager);
    }
    public class LocalBinder extends Binder {
        public EthereumService getServiceInstance(){
            return EthereumService.this;
        }
    }

    public void registerClient(EthereumServiceInterface client){
        this.callbacks.add(client);
    }
    public void unregisterClient(EthereumServiceInterface client){
        this.callbacks.remove(client);
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

                    nodeManager = new EthereumNodeManager(EthereumService.dataDir + EthereumService.GETH_IPC_FILE);
                    dispatchCallback();

                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                } finally {
                    checkFileThread.interrupt();
                }
            }
        });
        checkFileThread.start();
    }
    private void dispatchCallback(){
        for(EthereumServiceInterface client : this.callbacks){
            client.onEthereumServiceReady(this.nodeManager);
        }
    }

    public EthereumNodeManager getNodeManager() {
        return nodeManager;
    }

    @Override
    public void onDestroy() {
        try {
            gethThread.interrupt();
            checkFileThread.interrupt();
            nodeManager.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

}
