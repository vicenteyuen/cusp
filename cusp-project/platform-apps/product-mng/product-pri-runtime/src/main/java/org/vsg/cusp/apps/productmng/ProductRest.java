package org.vsg.cusp.apps.productmng;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author vison ruan
 *
 */

@Path("/products")
public class ProductRest {



	private static Logger logger = LoggerFactory.getLogger(ProductRest.class);

	@GET
	@Path("/{id}")
	public void getProduct(@Suspended AsyncResponse asyncResponse, @PathParam("id") String productId)
			throws InterruptedException {

		try {
			logger.info("call method.");
			// --- call response handle ---
			//ResponseBuilder rb = Response.ok("hello world, VISON , my dear");
			//Map<String,String> map = new HashMap<String,String>();
			//map.put("test", "hello world, VISON , my dear");
			ResponseBuilder rb = Response.ok("base");
			Response resp = rb.type(MediaType.TEXT_PLAIN).build();

			asyncResponse.resume(resp);


			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {

		}

	}

}
