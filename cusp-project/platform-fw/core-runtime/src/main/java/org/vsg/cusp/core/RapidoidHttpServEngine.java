/**
 * 
 */
package org.vsg.cusp.core;

import org.rapidoid.setup.On;

/**
 * @author Vicente Yuen
 *
 */
public class RapidoidHttpServEngine implements ServEngine {

	/* (non-Javadoc)
	 * @see org.vsg.cusp.core.ServEngine#start()
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub
		String host = "localhost";
		int port = 8080;

		On.address(host).port(port);
		
		On.page("/hi").mvc("Hello <b>world</b>!");
	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.core.ServEngine#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
