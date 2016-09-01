package com.web3j.blockchain.automotive;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.math.BigDecimal;
import java.math.BigInteger;

import web3j.module.objects.Block;
import web3j.module.objects.Hash;
import web3j.module.objects.Transaction;
import web3j.module.objects.TransactionReceipt;
import web3j.module.objects.TransactionRequest;
import web3j.solidity.SolidityUtils;


public class MainActivity extends EthereumActivity implements View.OnClickListener{

    static final String TAG = MainActivity.class.getSimpleName();

    Button button;
    Button tx;

    String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);


        tx = (Button) findViewById(R.id.tx);
        tx.setOnClickListener(this);

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
                    Block<Hash> b = web3J.eth.block(BigInteger.valueOf(0),Hash.class);
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
                    Transaction transaction = web3J.eth.transaction(Hash.valueOf("0x253c0d8019558dae73af30fe2281e9177df696c26545b520770a64cd475b48f4"));
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
                    TransactionReceipt transactionReceipt = web3J.eth.transactionReceipt(Hash.valueOf("0x253c0d8019558dae73af30fe2281e9177df696c26545b520770a64cd475b48f4"));
                }catch(Exception e){
                    Log.e(TAG,e.getMessage());
                }
            }
        }).start();
    }

    private void createAccount(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    account = web3J.personal.newAccount("toto");
                    Log.i(TAG,"new account : :"+account);


                }catch(Exception e){
                    Log.e(TAG,e.getMessage());
                }
            }
        }).start();
    }

    private void sendTransaction(){
        try {
            boolean unlocked = web3J.personal.unlockAccount(account,"toto",3600);
            if( unlocked ) {
                BigDecimal amount = SolidityUtils.toWei("2", "ether");
                String amountHex = SolidityUtils.toHex(amount);
                TransactionRequest tx = new TransactionRequest(account,"0xf1e04ff9007ee1e0864cd39270a407c71b14b7e2",amountHex,"4 u");

                Hash txHash = web3J.eth.sendTransaction(tx);

            }
        }catch(Exception e){
            Log.e(TAG,e.getMessage());
        }
    }

    private void enableButtons(final boolean enable){
        button.setEnabled(enable);
    }

    @Override
    public void onClick(View view) {
        if (view == button){
            createAccount();
        } else if(view == tx){
            sendTransaction();
        }
    }
}
