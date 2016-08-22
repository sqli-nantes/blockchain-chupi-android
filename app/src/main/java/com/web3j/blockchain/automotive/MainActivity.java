package com.web3j.blockchain.automotive;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends EthereumActivity{

    static final String TAG = MainActivity.class.getSimpleName();

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
        enableButtons(false);
        super.onResume();
    }

    @Override
    public void onEthereumServiceReady() {
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                enableButtons(true);
            }
        });
        super.onEthereumServiceReady();
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
