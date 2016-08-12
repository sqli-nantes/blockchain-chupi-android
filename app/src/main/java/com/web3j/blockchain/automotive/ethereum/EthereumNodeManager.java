package com.web3j.blockchain.automotive.ethereum;

import com.web3j.blockchain.automotive.ethereum.contract.ContractFactory;
import com.web3j.blockchain.automotive.ethereum.modules.Admin;
import com.web3j.blockchain.automotive.ethereum.network.RequestManager;

import org.json.JSONArray;

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


/*    public ContractFactory contract(JSONArray abi){
        return new ContractFactory(abi);
    }*/

    public void stop() throws IOException {
        requestManager.stop();
    }


}
