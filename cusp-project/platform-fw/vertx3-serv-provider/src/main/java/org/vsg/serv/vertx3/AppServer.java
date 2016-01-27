package org.vsg.serv.vertx3;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.serv.vertx3.util.Runner;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Hello world!
 *
 */
public class AppServer extends AbstractVerticle  {
	
	
	private static Logger logger = LoggerFactory.getLogger(AppServer.class);
	
	private Map<String, JsonObject> products = new HashMap<>();
	
    public static void main( String[] args )
    {
    	Runner.runExample(AppServer.class);
    }
    
    
    // ---- run vertx server ---
    @Override
    public void start() throws Exception {
    	
    	
    	Injector injector = Guice.createInjector(new ControllerScanModule());
    	
    	
        Router router = Router.router(vertx);
/*
        router.route().handler(routingContext -> {
          routingContext.response().putHeader("content-type", "text/html").end("Hello World!");
        });
*/
        scanRegisterController();
        router.route().handler(BodyHandler.create());
        
        // --- scan restful url ---
        
        router.get("/products/:productID").handler(this::handleGetProduct);
        router.put("/products/:productID").handler(this::handleAddProduct);
        router.get("/products").handler(this::handleListProducts);        

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
        logger.info("Startuped server on port : [8080]");
        
    }
    
    private void scanRegisterController() {
    	// --- scan all contraoller
    }
    
    
    
    private void handleGetProduct(RoutingContext routingContext) {
        String productID = routingContext.request().getParam("productID");

        HttpServerResponse response = routingContext.response();
        if (productID == null) {
          sendError(400, response);
        } else {
          JsonObject product = products.get(productID);
         
          if (product == null) {
            sendError(404, response);
          } else {
            response.putHeader("content-type", "application/json").end(product.encodePrettily());
          }
        }
      }

      private void handleAddProduct(RoutingContext routingContext) {
        String productID = routingContext.request().getParam("productID");
        System.out.println("product Id : " + productID);
        HttpServerResponse response = routingContext.response();
        if (productID == null) {
          sendError(400, response);
        } else {
          JsonObject product = routingContext.getBodyAsJson();
          if (product == null) {
            sendError(400, response);
          } else {
            products.put(productID, product);
            response.end();
          }
        }
      }

      private void handleListProducts(RoutingContext routingContext) {
        JsonArray arr = new JsonArray();
        products.forEach((k, v) -> arr.add(v));
        routingContext.response().putHeader("content-type", "application/json").end(arr.encodePrettily());
      }

      private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
      }

      private void setUpInitialData() {
        addProduct(new JsonObject().put("id", "prod3568").put("name", "Egg Whisk").put("price", 3.99).put("weight", 150));
        addProduct(new JsonObject().put("id", "prod7340").put("name", "Tea Cosy").put("price", 5.99).put("weight", 100));
        addProduct(new JsonObject().put("id", "prod8643").put("name", "Spatula").put("price", 1.00).put("weight", 80));
      }

      private void addProduct(JsonObject product) {
        products.put(product.getString("id"), product);
      }    
    
}
