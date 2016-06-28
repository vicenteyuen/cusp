package org.vsg.cusp.engine.zmq;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class ReqRepWorker implements RunnableFuture {
	
	private static Logger logger = LoggerFactory.getLogger( ReqRepWorker.class );	
	

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object get() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(long timeout, TimeUnit unit) throws InterruptedException,
			ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		
		Context context = ZMQ.context (1);
		
        Socket responder = context.socket (ZMQ.REP);
        responder.connect ("tcp://localhost:5560");

        try {
        	logger.info("ReqRepWorker Running. ");
        	
			while (!Thread.currentThread ().isInterrupted ()) {
			    //  Wait for next request from client
			    String string = responder.recvStr (0);
			    
			    if (logger.isDebugEnabled()) {
			    	logger.debug("Received request: [%s] " + string);
			    }

			    //  Do some 'work'
			    Thread.sleep (1000);

			    //  Send reply back to client
			    responder.send ("World");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			logger.info("ReqRepWorker shutdown. ");
		}
        
        //  We never get here but clean up anyhow
        responder.close();
        context.term();
	}

	
	
}
