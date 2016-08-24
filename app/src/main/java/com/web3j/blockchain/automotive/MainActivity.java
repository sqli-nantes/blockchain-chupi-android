package com.web3j.blockchain.automotive;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.List;

import rx.Subscriber;
import web3j.exception.Web3JException;
import web3j.module.objects.Block;
import web3j.module.objects.NodeInfo;
import web3j.module.objects.Peer;
import web3j.module.objects.Transaction;
import web3j.module.objects.TransactionReceipt;


public class MainActivity extends EthereumActivity implements View.OnClickListener{

    static final String TAG = MainActivity.class.getSimpleName();

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        enableButtons(false);
        super.onResume();
    }

    @Override
    public void onEthereumServiceReady() {
        super.onEthereumServiceReady();
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                enableButtons(true);
            }
        });
    }

    private void getBlock(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Block b = web3J.eth.blockByNumber(BigInteger.valueOf(0),false);
                }catch(Exception e){
                    Log.e(TAG,e.getMessage());
                }
            }
        }).start();
    }
    private void getTransaction(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Transaction transaction = web3J.eth.transactionByHash("0x253c0d8019558dae73af30fe2281e9177df696c26545b520770a64cd475b48f4");
                }catch(Exception e){
                    Log.e(TAG,e.getMessage());
                }
            }
        }).start();
    }
    private void getTransactionReceipt(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TransactionReceipt transactionReceipt = web3J.eth.transactionReceipt("0x253c0d8019558dae73af30fe2281e9177df696c26545b520770a64cd475b48f4");
                }catch(Exception e){
                    Log.e(TAG,e.getMessage());
                }
            }
        }).start();
    }



    private void enableButtons(final boolean enable){
        button.setEnabled(enable);
    }

    @Override
    public void onClick(View view) {
        if (view == button){
            //getBlock();
            getTransaction();
            //getTransactionReceipt();
        }
    }
}
