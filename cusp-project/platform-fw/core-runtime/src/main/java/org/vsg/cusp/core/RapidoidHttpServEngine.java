/**
 * 
 */
package org.vsg.cusp.core;

import java.util.Map;

import org.rapidoid.net.Server;
import org.rapidoid.setup.On;
import org.rapidoid.setup.Setup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vicente Yuen
 *
 */
public class RapidoidHttpServEngine implements ServEngine , Runnable {

	private static Logger logger = LoggerFactory.getLogger(RapidoidHttpServEngine.class);
	
	private Map<String, String> arguments;
	
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
		if (null != serv) {
			// --- shutdown server ---
			if (serv.isActive()) {
				serv.shutdown();
			}
		}

	}
	
	private Server serv;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String host = arguments.get("host");
		int port = Integer.parseInt( arguments.get("port") );

		try {

			Setup setup = On.setup();
			setup.address(host);
			setup.port(port);
			
			serv = setup.listen();
			logger.info("listen http port : [" + port + "].");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	

}
