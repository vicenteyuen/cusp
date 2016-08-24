package org.vsg.cusp.apps.productmng;

import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.concurrent.AsyncResult;
import org.vsg.cusp.concurrent.Callback;
import org.vsg.cusp.core.Handler;
import org.vsg.cusp.event.DefaultRuntimeParams;
import org.vsg.cusp.event.EventTrigger;
import org.vsg.cusp.event.RuntimeParam;
import org.vsg.deferred.DeferredManager;

/**
 * 
 * @author vison ruan
 *
 */

@Path("/products")
public class ProductRest {

	@Inject
	private EventTrigger eventTrigger;

	private static Logger logger = LoggerFactory.getLogger(ProductRest.class);

	@GET
	@Path("/{id}")
	public void getProduct(AsyncResponse asyncResponse, @PathParam("id") String productId, int hello)
			throws InterruptedException {

		try {
			logger.info("call method.");
			// --- call response handle ---
			ResponseBuilder rb = Response.ok("hello world, VISON , my dear");
			rb.type(MediaType.TEXT_PLAIN);

			DeferredManager dm = DeferredManager.loadFactory(DeferredManager.class);
			dm.when((result) -> {
				try {
					// --- call remove event ---
					DefaultRuntimeParams runParams = new DefaultRuntimeParams();
					runParams.addRuntimeParam(new RuntimeParam("testparam1", String.class, "test value1"));
					eventTrigger.fire("testCase1", runParams, (res ,error) -> {
						System.out.println("invoke test case 1" + res);

					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} , (result) -> {

				try {
					DefaultRuntimeParams runParams2 = new DefaultRuntimeParams();
					runParams2.addRuntimeParam(new RuntimeParam("testparam2", Integer.TYPE, 5));
					eventTrigger.fire("testCase2", runParams2, new Callback() {

						@Override
						public void onDone(Object result, Throwable error) throws Exception {
							System.out.println("invoke test case 2" + result);
						}

					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			},(result)->{
				try {
					DefaultRuntimeParams runParams3 = new DefaultRuntimeParams();
					runParams3.addRuntimeParam(new RuntimeParam("testparam3", Integer.TYPE, 10));
					eventTrigger.fire("testCase3", runParams3, new Callback() {

						@Override
						public void onDone(Object result, Throwable error) throws Exception {
							System.out.println("invoke test case 3" + result);

						}

					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}

			).succeed(r -> {

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

				asyncResponse.resume("get out");

			});



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {

		}

	}

}
