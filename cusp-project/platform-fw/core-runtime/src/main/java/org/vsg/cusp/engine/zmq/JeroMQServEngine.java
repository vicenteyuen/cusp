/**
 * 
 */
package org.vsg.cusp.engine.zmq;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.core.Container;
import org.vsg.cusp.core.ServEngine;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * @author Vicente Yuen
 *
 */
public class JeroMQServEngine implements ServEngine , Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(JeroMQServEngine.class);	
	
	private Map<String, String> arguments;	
	
	private Container container;		

	@Override
	public void setRunningContainer(Container container) {
		// TODO Auto-generated method stub
		this.container = container;
	}

	@Override
	public void init(Map<String, String> arguments) {
		// TODO Auto-generated method stub
		this.arguments = arguments;		
	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.core.ServEngine#start()
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub
		Thread threadHook = new Thread(this);
		threadHook.start();
	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.core.ServEngine#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub


	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
        Context context = ZMQ.context(1);
        
        logger.info("boot start mq : " + 5559);

        //  Socket facing clients
        Socket frontend = context.socket(ZMQ.ROUTER);
        frontend.bind("tcp://*:5559");

        //  Socket facing services
        Socket backend = context.socket(ZMQ.DEALER);
        backend.bind("tcp://*:5560");

        //  Start the proxy
        ZMQ.proxy (frontend, backend, null);

        //  We never get here but clean up anyhow
        frontend.close();
        backend.close();
        context.term();		
	}

	
	
}
