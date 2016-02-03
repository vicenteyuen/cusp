package org.vsg.cusp.sysmng;

import io.vertx.core.Vertx;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	public Response printMessage(@PathParam("param") String msg) {
		
		String result = "Restful example" + msg;



		
		return Response.status(200).entity(result).build();
		
	}

	
}
