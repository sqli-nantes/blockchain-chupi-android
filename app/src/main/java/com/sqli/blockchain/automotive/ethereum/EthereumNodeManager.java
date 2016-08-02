package com.sqli.blockchain.automotive.ethereum;

import com.sqli.blockchain.automotive.ethereum.modules.Admin;
import com.sqli.blockchain.automotive.ethereum.network.RequestManager;

import java.io.IOException;

/**
 * Created by gunicolas on 27/07/16.
 */
public class EthereumNodeManager {

    public Admin admin;
    RequestManager requestManager;

    public EthereumNodeManager(String ipcFilePath) throws IOException {

        requestManager = new RequestManager(ipcFilePath);
        admin = new Admin(requestManager);

    }

    public void stop() throws IOException {
        requestManager.stop();
    }


}
