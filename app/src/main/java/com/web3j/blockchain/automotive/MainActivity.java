package com.web3j.blockchain.automotive;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends EthereumActivity{

    static final String TAG = MainActivity.class.getSimpleName();

    Button nodeInfoSyncButton;
    Button nodeInfoASyncButton;

    Button peersSyncButton;
    Button peersASyncButton;

    Button listAccountsSyncButton;
    Button listAccountsASyncButton;

    Button unlockLastAccountButton;
    EditText unlockLastAccountEdittext;

    Button newAccountsButton;
    EditText newAccountEditText;

    Button addPeerButton;
    EditText addPeerEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonListener buttonListener = new ButtonListener();

        nodeInfoSyncButton = (Button) findViewById(R.id.node_info_sync);
        nodeInfoSyncButton.setOnClickListener(buttonListener);
        nodeInfoASyncButton = (Button) findViewById(R.id.node_info_async);
        nodeInfoASyncButton.setOnClickListener(buttonListener);

        peersSyncButton = (Button) findViewById(R.id.peers_sync);
        peersSyncButton.setOnClickListener(buttonListener);
        peersASyncButton = (Button) findViewById(R.id.peers_async);
        peersASyncButton.setOnClickListener(buttonListener);

        listAccountsSyncButton = (Button) findViewById(R.id.listaccounts_sync);
        listAccountsSyncButton.setOnClickListener(buttonListener);
        listAccountsASyncButton = (Button) findViewById(R.id.listaccounts_async);
        listAccountsASyncButton.setOnClickListener(buttonListener);

        addPeerButton = (Button) findViewById(R.id.add_peer_button);
        addPeerButton.setOnClickListener(buttonListener);
        addPeerEditText = (EditText) findViewById(R.id.add_peer_edit);

        newAccountsButton = (Button) findViewById(R.id.newaccount_button);
        newAccountsButton.setOnClickListener(buttonListener);
        newAccountEditText = (EditText) findViewById(R.id.newaccount_edittext);

        unlockLastAccountButton = (Button) findViewById(R.id.unlockaccount_button);
        unlockLastAccountButton.setOnClickListener(buttonListener);
        unlockLastAccountEdittext = (EditText) findViewById(R.id.unlockaccount_edittext);

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

    private void enableButtons(final boolean enable){
        nodeInfoSyncButton.setEnabled(enable);
        nodeInfoASyncButton.setEnabled(enable);
        peersSyncButton.setEnabled(enable);
        peersASyncButton.setEnabled(enable);
        addPeerButton.setEnabled(enable);
        listAccountsASyncButton.setEnabled(enable);
        listAccountsSyncButton.setEnabled(enable);
        newAccountsButton.setEnabled(enable);
    }

    private class ButtonListener implements View.OnClickListener {


        /*void nodeInfoSyncButton(){
            NodeInfo nodeInfo = web3J.admin.nodeInfo();
            Log.d(TAG,"nodeInfo : "+nodeInfo.toString());
        }
        void nodeInfoASyncButton(){
            web3J.admin.getNodeInfo().subscribe(new Subscriber<NodeInfo>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG,e.getMessage());
                }

                @Override
                public void onNext(NodeInfo nodeInfo) {
                    Log.d(TAG,"nodeInfo : "+nodeInfo.toString());
                }
            });
        }
        void peerSyncButton(){List<Peer> peers = web3J.admin.peers();
            Log.d(TAG,"peers : ");
            for(Peer p : peers){
                Log.d(TAG,p.toString());
            }}
        void peerASyncButton(){
            web3J.admin.getPeers().subscribe(new Subscriber<List<Peer>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"error : "+e.getMessage());
            }

            @Override
            public void onNext(List<Peer> peers) {
                Log.d(TAG,"peers : ");
                for(Peer p : peers){
                    Log.d(TAG,p.toString());
                }
            }
        });
        }
        void addPeerButton(){
            String peer = addPeerEditText.getText().toString();
            if( peer == null || peer.length() == 0 ){
                peer = "enode://5d379e82db636134dda0b3ecca399bb8d502508da8950a64af9601fb5e1798626e1b214b589a6e03f6adda8c9f64abc919028765937f896f2df6463154fbcc13@10.33.44.182:30301";
            }
            String successMessage = "Error adding peer";
            try{
                boolean added = web3J.admin.addPeer(peer);
                if( added ) successMessage = "Peer added with success";
            }catch(Web3JException e){
                Log.e(TAG,e.getMessage());
            }
            finally {
                Toast.makeText(MainActivity.this,successMessage,Toast.LENGTH_SHORT).show();
            }
        }
        void listAccountsSyncButton(){
            String successMessage = "erreur récupération liste";
            try {
                List<String> accounts = web3J.personal.listAccounts();
                successMessage = "[";
                for(String account : accounts){
                    successMessage += account+",";
                }
                successMessage += "]";
            }catch(Web3JException e){
                Log.e(TAG,e.getMessage());
            }finally {
                Toast.makeText(MainActivity.this,successMessage,Toast.LENGTH_SHORT).show();
            }
        }
        void listAccountsASyncButton(){
            web3J.personal.listAccountsAsync().subscribe(new Subscriber<List<String>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG,e.getMessage());
                }

                @Override
                public void onNext(List<String> accounts) {
                    String accountsString = "accounts:[";
                    for(String account : accounts)
                        accountsString+=account+",";
                    Log.d(TAG,accountsString);
                }
            });
        }
        void newAccountButton(){
            String retMsg = "erreur creation compte";
            String passwd = newAccountEditText.getText().toString();
            if(passwd.length() == 0) passwd = "toto";
            try {
                String address = web3J.personal.newAccount(passwd);
                retMsg = "new account : "+address;
            }catch(Web3JException e){
                Log.d(TAG,e.getMessage());
            }
            finally {
                Toast.makeText(MainActivity.this,retMsg,Toast.LENGTH_SHORT).show();
            }
        }
        void unlockLastAccountButton(){
            String retMsg = "unlock error";
            String passwd = unlockLastAccountEdittext.getText().toString();
            try {
                List<String> accounts = web3J.personal.listAccounts();
                String lastAccount = accounts.get(accounts.size() - 1);
                web3J.personal.unlockAccount(lastAccount, passwd, 10);
                retMsg = "Unlock successful";
            } catch(Web3JException e){
                Log.e(TAG,e.getMessage());
            } finally {
                Toast.makeText(MainActivity.this,retMsg,Toast.LENGTH_SHORT).show();
            }
        }*/



        @Override
        public void onClick(View view) {
            /*if (view == nodeInfoSyncButton) nodeInfoSyncButton();
            else if (view == nodeInfoASyncButton) nodeInfoASyncButton();
            else if (view == peersSyncButton) peerSyncButton();
            else if (view == peersASyncButton) peerASyncButton();
            else if (view == addPeerButton) addPeerButton();
            else if( view == listAccountsSyncButton ) listAccountsSyncButton();
            else if( view == listAccountsASyncButton ) listAccountsASyncButton();
            else if( view == newAccountsButton ) newAccountButton();
            else if( view == unlockLastAccountButton ) unlockLastAccountButton();*/
        }
    }
}
