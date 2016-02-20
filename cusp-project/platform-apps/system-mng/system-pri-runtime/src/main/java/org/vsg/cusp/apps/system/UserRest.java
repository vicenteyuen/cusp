package org.vsg.cusp.apps.system;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.platform.utils.ServiceInvokerException;
import org.vsg.cusp.platform.utils.ServiceInvokerManager;

/**
 * 
 * @author vison ruan
 *
 */

@Path("/user")
public class UserRest {
	
	private static Logger logger = LoggerFactory.getLogger( UserRest.class );
	
	// --- get the proxy ---
	@Inject
	private ServiceInvokerManager serviceInvokerManager;

	
	@GET
	@Path("/{id}")
	public void getOneUser(
			@Suspended AsyncResponse asyncResponse , 
			@PathParam("id") String userId) {
		// mark params
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		
		// --- call service verticle ---
		try {
			serviceInvokerManager.invokeService("class:org.vsg.cusp.apps.system.service.UserServiceFacadeImpl:saveUser", params, invokeResult -> {
				// --- logic is ok 
				if ( invokeResult.isSuccess() ) {
					
				} else {
					
				}
				Response response = Response.ok("hello world, VISON123").type(MediaType.TEXT_PLAIN).build();
				asyncResponse.resume(response);
			});
		} catch (ServiceInvokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		vertx.deployVerticle("service:vertx.mods.vertx-services" , res -> {
			
			System.out.println("product : " + userId + " , " + res.succeeded() );

			// --- call response handle ---
            Response jaxrs = Response.ok("hello world, VISON").type(MediaType.TEXT_PLAIN).build();
            asyncResponse.resume(jaxrs);	


			
			
			
		});
		*/


		
	}

	
}
