package org.vsg.cusp.apps.productmng;

import java.util.LinkedHashMap;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.vsg.cusp.eventbus.AsyncResult;
import org.vsg.cusp.eventbus.EventBus;
import org.vsg.cusp.eventbus.EventFlow;
import org.vsg.cusp.eventbus.EventFlowManager;
import org.vsg.cusp.eventbus.Handler;



/**
 * 
 * @author vison ruan
 *
 */

@Path("/products")
public class ProductRest {
	
	@Inject
	private EventBus eventBus;
	
	
	private EventFlowManager efm;
	
	@GET
	@Path("/{id}")
	public void getProduct(AsyncResponse asyncResponse , @PathParam("id") String productId , int test) throws InterruptedException  {
		
		EventFlow ef = null;
		
		try {
			// --- call response handle ---
			ResponseBuilder rb = Response.ok("hello world, VISON , my dear");
			rb.type( MediaType.TEXT_PLAIN );
			
			System.out.println("show event bus : " + eventBus);
			
			
			ef = efm.getFlow("product/test.example");
			
			Handler<AsyncResult<Future>> eventHandler = new Handler<AsyncResult<Future>>() {

				@Override
				public void handle(AsyncResult<Future> event) {
					// TODO Auto-generated method stub
					Future f = event.result();
					
					if (event.succeeded()) {
						// --- success code 
						Response jaxrs = rb.build();
						asyncResponse.resume(jaxrs);
						
					}
					
				}
				
			};
			
			ef.fire("org.vsg.cusp.evetimst.case1", new LinkedHashMap<String,Object>(), eventHandler);
			ef.fire("org.vsg.cusp.evetimst.case2", new LinkedHashMap<String,Object>(), eventHandler);
			


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != ef) {
				ef.fireAtEnd( new Handler<AsyncResult>() {

					@Override
					public void handle(AsyncResult event) {
						// TODO Auto-generated method stub
						
					}
					
				});
			}
		}
		
        /*		
		CountDownLatch latch = new CountDownLatch(2);
		vertx.eventBus().localConsumer("moduleStarted").handler(message -> {
			System.out.println(message.body());
			latch.countDown();
		});

		
		// --- call service verticle ---
		vertx.deployVerticle("service:vertx.mods.vertx-services" , res -> {
			
			System.out.println("product : " + productId + " , " + res.succeeded() );

			// --- call response handle ---
            Response jaxrs = Response.ok("hello world, VISON").type(MediaType.TEXT_PLAIN).build();
            asyncResponse.resume(jaxrs);	
			
		});
		*/


		
	}

	
}
