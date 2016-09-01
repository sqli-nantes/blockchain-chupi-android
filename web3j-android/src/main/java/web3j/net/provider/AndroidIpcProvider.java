package web3j.net.provider;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;

import java.io.IOException;

import web3j.exception.Web3JException;

/**
 * Created by gunicolas on 27/07/16.
 */
public class AndroidIpcProvider extends IpcAbstractProvider {

    LocalSocket socket;

    public AndroidIpcProvider(String _ipcFilePath) throws Web3JException {
        super(_ipcFilePath);
    }

    @Override
    protected void createSocket() throws IOException {
        this.socket = new LocalSocket();
        this.socket.connect(new LocalSocketAddress(ipcFilePath, LocalSocketAddress.Namespace.FILESYSTEM));
        this.outputStream = this.socket.getOutputStream();
        this.inputStream = this.socket.getInputStream();
    }

    @Override
    protected void closeSocket() throws IOException {
        if( this.socket != null ) {
            this.socket.close();
        }
    }


}
