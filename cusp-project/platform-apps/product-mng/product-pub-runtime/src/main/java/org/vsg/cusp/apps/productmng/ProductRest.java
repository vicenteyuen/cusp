package org.vsg.cusp.apps.productmng;

import io.vertx.core.Vertx;

import java.util.concurrent.CountDownLatch;

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

@Path("/products")
public class ProductRest {
	
	
	// --- get the proxy ---
	@Inject
	private Vertx vertx;

	
	@GET
	@Path("/{param}")
	public Response printMessage(@PathParam("param") String msg) {
		
		String result = "Restful example" + msg;

		CountDownLatch latch = new CountDownLatch(2);
		vertx.eventBus().localConsumer("moduleStarted").handler(message -> {
			System.out.println(message.body());
			latch.countDown();
		});

		
		// --- call service verticle ---
		vertx.deployVerticle("service:vertx-services" , res -> {
			System.out.println(res.succeeded());
		});



		
		return Response.status(200).entity(result).build();
		
	}

	
}
