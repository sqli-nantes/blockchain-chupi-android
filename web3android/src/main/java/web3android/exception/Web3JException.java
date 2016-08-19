package web3android.exception;

/**
 * Created by gunicolas on 17/08/16.
 */
public class Web3JException extends RuntimeException {

    String message;

    public Web3JException(Exception e){
        this.message = e.getMessage();
    }

    public Web3JException(String message) {
         this.message = message;
    }
}
