package web3android.net.provider;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import rx.Subscriber;
import web3android.exception.Web3JException;
import web3android.net.Response;

/**
 * Created by gunicolas on 27/07/16.
 */
public class IpcProvider extends Provider {


    private static final Charset CHARSET = StandardCharsets.UTF_8;

    final String ipcFilePath;
    LocalSocket socket;
    DataOutputStream out;
    Thread listeningThread;

    BufferedReader in;

    public IpcProvider(String _ipcFilePath) throws Web3JException {
        super();
        this.ipcFilePath = _ipcFilePath;
//        createSocket();
//        listenSocket();
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
        listeningThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String line;
                        while (in.ready() && (line = in.readLine()) != null) {

                            Response response = new Gson().fromJson(line, Response.class);

                            int requestId = response.id;

                            Object request[] = requestQueue.get(requestId);

                            if( request != null && request.length>0 ) {

                                Subscriber<Object> subscriber = (Subscriber<Object>) request[0];
                                if (subscriber != null){

                                    if (!response.isError()) {

                                        Method method = (Method) request[1];
                                        ParameterizedType returnType = (ParameterizedType) method.getGenericReturnType();

                                        Object data = new Gson().fromJson(response.result,returnType);

                                        subscriber.onNext(data);
                                        subscriber.onCompleted();

                                    } else {
                                        subscriber.onError(new Web3JException(response.error.message));
                                    }

                                    requestQueue.remove(requestId);

                                } // else just ignored

                            } // else just ignored
                        }
                        Thread.sleep(500);
                    }
                }
                catch(Exception e){
                   e.getStackTrace();
                }
            }
        });
        listeningThread.start();
    }

    @Override
    protected void sendThroughMedia(String requestString) throws Web3JException {
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
            this.listeningThread.interrupt();
        }
    }
}
