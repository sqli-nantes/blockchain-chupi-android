package web3j;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigInteger;
import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import web3j.module.objects.Block;
import web3j.net.Request;
import web3j.net.provider.Provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by gunicolas on 24/08/16.
 */
public class InvocationHandlerTest {

    @Mock
    Provider mockProvider;

    @Captor
    ArgumentCaptor<Request<Block>> captor;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private Web3J web3j;

    @Before
    public void setup() {
        web3j = new Web3J.Builder()
                .provider(mockProvider)
                .build();
    }

    @Test
    public void synchronousMethodReturnedObjectTest() throws Exception{
        final Block block = new Block();
        Request blockRequest = new Request<Block>("","", Block.class);
        Observable.OnSubscribe subscriber = new Observable.OnSubscribe<Block>() {
            @Override
            public void call(Subscriber<? super Block> subscriber) {
                subscriber.onNext(block);
                subscriber.onCompleted();
            }
        };
        Observable observable = Observable.create(subscriber);
        when(mockProvider.sendRequest(blockRequest)).thenReturn(observable);
        Observable<Block> b = web3j.eth.getBlockByHash("");
        b.subscribe(new Subscriber<Block>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Block returnedBlock) {
                assertEquals(block,returnedBlock);
            }
        });
    }

    @Test
    public void asynchronousMethodReturnedObjectTest() throws Exception{
        Block block = new Block();
        Request getBlockRequest = new Request<Block>("", "", Block.class);
        Observable observable = Observable.just(block);
        when(mockProvider.sendRequest(getBlockRequest)).thenReturn(observable);

        Block returnedBlock = web3j.eth.blockByHash("");
        assertTrue(returnedBlock.equals(block));
    }

    @Test
    public void synchronousMethodRequestTest() throws Exception{
        //TODO error with eth.block
        web3j.eth.getBlockByHash("0x63cb70cdcef14c3ba7572b5171cf09df2bc7685a8e790588260a1396856c2482");
        verify(mockProvider).sendRequest(captor.capture());
        Request<Block> req = captor.<Request<Block>>getValue();
        assertEquals(req.getReturnType(),Block.class);
        assertEquals("eth_getBlock",req.getMethodCall());
        assertEquals("[\"0x63cb70cdcef14c3ba7572b5171cf09df2bc7685a8e790588260a1396856c2482\"]",req.getArguments());

    }

    @Test
    public void asynchronousMethodRequestTest() throws Exception{
        web3j.eth.getBlockByHash("0x63cb70cdcef14c3ba7572b5171cf09df2bc7685a8e790588260a1396856c2482");
        verify(mockProvider).sendRequest(captor.capture());
        Request<Block> req = captor.<Request<Block>>getValue();
        assertEquals(req.getReturnType(),Block.class);
        assertEquals("eth_getBlock",req.getMethodCall());
        assertEquals("[\"0x63cb70cdcef14c3ba7572b5171cf09df2bc7685a8e790588260a1396856c2482\"]",req.getArguments());
        assertEquals(0,req.getId());
        assertEquals(new ArrayList<Subscriber>(),req.getSubscribers());

    }

    @Test
    public void asynchronousMethodRequestBigIntegerTest() throws Exception{
        web3j.eth.getBlockByNumber(new BigInteger("0"));
        verify(mockProvider).sendRequest(captor.capture());
        Request<Block> req = captor.<Request<Block>>getValue();
        assertEquals(req.getReturnType(),Block.class);
        assertEquals("eth_getBlock",req.getMethodCall());
        assertEquals("[0]",req.getArguments());
        assertEquals(0,req.getId());
        assertEquals(new ArrayList<Subscriber>(),req.getSubscribers());
    }
}
