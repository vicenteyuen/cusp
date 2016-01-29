/**
 * 
 */
package org.vsg.app.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author ruanweibiao
 *
 */
@Path("/helloworld")
public class HelloWorld {
	
	
	
	
	
	@GET
	@Path("/getpath")
	@Produces("text/plain")
	public String getResponse() {
		

		
		System.out.println("run ok");
		
		return "hello my boy.";
	}
	
	@GET
	@Path("/getpath2")
	@Produces("text/plain")
	public String getResponse2() {
		

		
		System.out.println("run ok");
		
		return "hello my boy.";
	}	
	

}
