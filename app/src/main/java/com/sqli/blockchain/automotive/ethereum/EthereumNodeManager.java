package com.sqli.blockchain.automotive.ethereum;

import com.sqli.blockchain.automotive.ethereum.modules.Admin;

import java.io.IOException;

/**
 * Created by gunicolas on 27/07/16.
 */
public class EthereumNodeManager {

    public Admin admin;

    public EthereumNodeManager(String ipcFilePath) throws IOException {

        RequestManager requestManager = new RequestManager(ipcFilePath);
        admin = new Admin(requestManager);

    }


}
