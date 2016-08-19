package com.web3j.blockchain.automotive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import web3android.Web3J;


public class MainActivity extends AppCompatActivity implements EthereumService.EthereumServiceInterface{

    static final String TAG = MainActivity.class.getSimpleName();

    AutomotiveApplication app;

    Web3J web3J;

    Button nodeInfoButton;
    Button peersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonListener buttonListener = new ButtonListener();

        nodeInfoButton = (Button) findViewById(R.id.node_info);
        nodeInfoButton.setOnClickListener(buttonListener);

        peersButton = (Button) findViewById(R.id.peers);
        peersButton.setOnClickListener(buttonListener);

    }

    @Override
    protected void onResume() {
        super.onResume();

        app = (AutomotiveApplication) getApplication();
        if( !app.isEthereumServiceStarted() )
            app.onResume();


        /*if(  app.getEthereumService() == null ){
            enableButtons(false);
            app.registerClient(this);
        } else if( app.getEthereumService().getNodeManager() == null) {
            enableButtons(false);
            app.registerClient(this);
        } else{
            this.web3J = app.getEthereumService().getNodeManager();
            enableButtons(true);
        }*/

    }

    @Override
    protected void onPause() {
        app.unregisterClient(this);
        app.onTerminate();
        super.onPause();
    }

    @Override
    public void onEthereumServiceReady() {
        Log.d(TAG,"geth is ready");
        this.web3J = null; //TODO instanciate web3Android
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                enableButtons(true);
            }
        });
        this.app.getEthereumService().unregisterClient(this);
    }

    private void enableButtons(final boolean enable){
        nodeInfoButton.setEnabled(enable);
        peersButton.setEnabled(enable);
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view == nodeInfoButton) {

            } else if (view == peersButton) {

            }
        }
    }
}
