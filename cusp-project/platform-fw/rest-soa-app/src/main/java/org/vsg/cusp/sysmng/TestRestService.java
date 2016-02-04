package org.vsg.cusp.sysmng;

import io.vertx.core.Vertx;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 
 * @author vison ruan
 *
 */

@Path("/message")
public class TestRestService {
	
	
	// --- get the proxy ---
	@Inject
	private Vertx vertx;

	
	@GET
	@Path("/{param}")
	public void printMessage(@PathParam("param") String msg , @Suspended final AsyncResponse asyncResponse) {
		
	      Thread t = new Thread()
	      {
	         @Override
	         public void run()
	         {
	            try
	            {
	               Response jaxrs = Response.ok("2000").type(MediaType.TEXT_PLAIN).build();
	               asyncResponse.resume(jaxrs);
	            }
	            catch (Exception e)
	            {
	               e.printStackTrace();
	            }
	         }
	      };
	      t.start();
		
	}

	
}
