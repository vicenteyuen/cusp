package org.vsg.cusp.engine.zmq;

import java.util.Objects;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.MessageEncoder;
import org.vsg.cusp.event.MessageInbox;
import org.vsg.cusp.event.impl.CodecManager;
import org.vsg.cusp.event.impl.WorkerTrigger;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class ReqRepWorker implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger( ReqRepWorker.class );	
	
	
	public ReqRepWorker() {
		
	}
	
	public ReqRepWorker(int workerPort) {
		this.workerPort = workerPort;
	}
	
	private MessageInbox msgInbox;

	public MessageInbox getMsgInbox() {
		return msgInbox;
	}

	@Inject	
	public void setMsgInbox(MessageInbox msgInbox) {
		this.msgInbox = msgInbox;
	}

	private  MessageEncoder encoder;
	
	public MessageEncoder getEncoder() {
		return encoder;
	}
	
	@Inject
	public void setEncoder(MessageEncoder encoder) {
		this.encoder = encoder;
	}

	
	private int workerPort = 5066;
	
	private  CodecManager codecManager;
	
	@Inject
	public void setCodecManager(CodecManager codecManager) {
		this.codecManager = codecManager;
	}
	
	@Override
	public void run() {
		StringBuilder clientSocket = new StringBuilder();
		
		Context context = ZMQ.context (zmq.ZMQ.ZMQ_IO_THREADS);
		
        Socket receiver = context.socket (ZMQ.PULL);
        receiver.connect ("tcp://localhost:5560");
        
		Socket requester = context.socket(ZMQ.PUSH);
		requester.connect("tcp://localhost:5561");
		
		
		Objects.requireNonNull( msgInbox , "Message Inbox is not null. ");

        try {
        	logger.info("ReqRepWorker Running. ");
        	
			while (!Thread.currentThread ().isInterrupted ()) {
			    //  Wait for next request from client
	            byte[] message;
	            while((message = receiver.recv(ZMQ.DONTWAIT)) != null) {
	            	
	            	// --- parse job message ---
	            	if (null != message) {
	            		Message<byte[]> msgRef = encoder.decode(message);
	            		
	            		msgInbox.receiveMsg( msgRef );
	            		
	            		/**
	            		 * fire comsumer object
	            		 */
	            		/*
	            		WorkerTrigger trggerService = new WorkerTrigger();
	            		trggerService.setCodecManager( codecManager );
	            		trggerService.setReplySocket( requester );
	            		trggerService.receiveMessage(msgRef);
	            		trggerService.trigger();
	            		*/
          		
	            		
	            	} else {

	            	}

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
