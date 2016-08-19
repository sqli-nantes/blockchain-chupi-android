package web3android.module.objects;

import java.math.BigDecimal;

/**
 * Created by gunicolas on 18/08/16.
 */
public class NodeInfo {

    public String enode;
    public String id;
    public String ip;
    public String listenAddr;
    public String name;
    public Ports ports;
    public Protocols protocols;

    public class Protocols{

        public Eth eth;

        public class Eth{
            public BigDecimal difficulty;
            public String genesis;
            public String head;
            public int network;
        }
    }

    public class Ports{
        public int discovery;
        public int listener;
    }

}
