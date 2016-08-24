package web3j.net.provider;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import rx.Subscriber;
import web3j.exception.Web3JException;
import web3j.gson.BigIntegerTypeAdapter;
import web3j.net.Request;
import web3j.net.Response;

/**
 * Created by gunicolas on 27/07/16.
 */
public class AndroidIpcProvider extends AbstractProvider {

    private static final String TAG = AndroidIpcProvider.class.getSimpleName();

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    final String ipcFilePath;
    LocalSocket socket;
    DataOutputStream out;
    Thread listeningThread;

    BufferedReader in;

    boolean listen;

    Gson gson;

    public AndroidIpcProvider(String _ipcFilePath) throws Web3JException {
        super();
        this.ipcFilePath = _ipcFilePath;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter(BigInteger.class,new BigIntegerTypeAdapter());
        gson = gsonBuilder.create();
        createSocket();
        listenSocket();
    }

    private void createSocket() throws Web3JException {
        socket = new LocalSocket();
        try {
            socket.connect(new LocalSocketAddress(ipcFilePath, LocalSocketAddress.Namespace.FILESYSTEM));
            out = new DataOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(),CHARSET.name()));
        } catch (IOException e) {
            throw new Web3JException(e);
        }
    }

    private void listenSocket(){
        listen = true;
        listeningThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (listen) {
                        String line;
                        while (in.ready() && (line = in.readLine()) != null) {

                            Log.d(TAG,line);
                            Response response = new Gson().fromJson(line, Response.class);

                            int requestId = response.id;

                            Request request = requestQueue.get(requestId);

                            if( request != null ) {

                                List<Subscriber> subscribers = request.getSubscribers();
                                if ( response.isError() ) {
                                    for(Subscriber subscriber : subscribers) {
                                        subscriber.onError(new Web3JException(response.error.message));
                                    }
                                } else {

                                    Object data = gson.fromJson(response.result, request.getReturnType());
                                    for(Subscriber subscriber : subscribers) {
                                        subscriber.onNext(data);
                                        subscriber.onCompleted();
                                    }
                                }
                                requestQueue.remove(requestId);

                            } // else just ignored
                        }
                        Thread.sleep(500);
                    }
                }
                catch(Exception e){
                    Log.e(TAG,e.getLocalizedMessage());
                }
            }
        });
        listeningThread.start();
    }

    @Override
    protected void sendThroughMedia(String requestString) throws Web3JException {
        Log.d(TAG,requestString);

        byte[] req = requestString.getBytes(CHARSET);
        try {
            out.write(req);
        } catch (IOException e) {
            throw new Web3JException(e);
        }
    }

    public void stop() throws IOException {
        if( this.socket != null ) {
            this.socket.close();
        }
        if( this.listeningThread != null ){
            this.listen = false;
        }
    }
}
