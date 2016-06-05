/**
 * 
 */
package org.vsg.cusp.startup;

import java.util.Map;

import org.rapidoid.setup.On;

/**
 * Mark and call fontend Server handle 
 * 
 * @author Vicente Yuen
 *
 */
public class FontendServer {
	
	
	private Map<String, java.io.Serializable> envProps = null;

	/**
	 * 
	 */
	public FontendServer() {
		// TODO Auto-generated constructor stub
	}
	
	void setEnvProps(Map<String, java.io.Serializable> inputEnvProps) {
		this.envProps = inputEnvProps;
	}
	
	
	
	public void startup() {
		String host = envProps.get("server.fe.host").toString();
		int port = Integer.parseInt( envProps.get("server.fe.port").toString() );
		On.address(host).port(port);
		
		On.page("/hi").mvc("Hello <b>world</b>!");
	}

}
