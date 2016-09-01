package web3j.net.provider;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import rx.Subscriber;
import web3j.exception.Web3JException;
import web3j.net.Response;

/**
 * Created by gunicolas on 31/08/16.
 */
public abstract class IpcAbstractProvider extends AbstractProvider {

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    OutputStream outputStream;
    InputStream inputStream;

    final String ipcFilePath;
    Thread listeningThread;

    DataOutputStream out;
    BufferedReader in;

    boolean listen;

    public IpcAbstractProvider(String _ipcFilePath) throws Web3JException {
        super();
        this.ipcFilePath = _ipcFilePath;
        createStreams();
    }

    protected abstract void createSocket() throws IOException;
    protected abstract void closeSocket() throws IOException;

    private void createStreams() throws Web3JException {
        try {
            createSocket();
            out = new DataOutputStream(outputStream);
            in = new BufferedReader(new InputStreamReader(inputStream, CHARSET));
        }catch(IOException e){
            throw new Web3JException(e);
        }
    }


    public void listen() throws Web3JException{
        listeningThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listen = true;
                    while (listen) {
                        try {
                            String line;
                            while (in.ready() && (line = in.readLine()) != null) {
                                System.out.println(line);
                                Response response = gson.fromJson(line, Response.class);

                                if (response.request != null) {

                                    List<Subscriber> subscribers = response.request.getSubscribers();
                                    if (response.isError()) {
                                        for (Subscriber subscriber : subscribers) {
                                            subscriber.onError(new Web3JException(response.error.message));
                                        }
                                    } else {
                                        for (Subscriber subscriber : subscribers) {
                                            subscriber.onNext(response.result);
                                            subscriber.onCompleted();
                                        }
                                    }
                                    requestQueue.remove(response.id);

                                } // else just ignored
                            }
                            Thread.sleep(500);
                        }catch(Exception e ){
                            System.out.println(e.getMessage());
                        }
                    }
                }
                catch(Exception e){
                    throw new Web3JException(e);
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
        if( this.listeningThread != null ){
            this.listen = false;
        }
        closeSocket();
    }
}
