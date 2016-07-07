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
	
	
	public ReqRepWorker() {
		
	}
	
	public ReqRepWorker(int workerPort) {
		this.workerPort = workerPort;
	}
	

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
	
	
	private int workerPort = 5066;
	
	

	@Override
	public void run() {
		StringBuilder clientSocket = new StringBuilder();
		
		Context context = ZMQ.context (1);
		
        Socket receiver = context.socket (ZMQ.PULL);
        receiver.connect ("tcp://localhost:5560");

        try {
        	logger.info("ReqRepWorker Running. ");
        	
			while (!Thread.currentThread ().isInterrupted ()) {
			    //  Wait for next request from client
	            byte[] task;
	            while((task = receiver.recv(ZMQ.DONTWAIT)) != null) {
	            	
	            	// --- parse job message ---
	            	
	                System.out.println("process task size : " + task.length);
	            }

			}
		} finally {
			logger.info("ReqRepWorker shutdown. ");
		}
        
        //  We never get here but clean up anyhow
        receiver.close();
        context.term();
	}

	
	
}
