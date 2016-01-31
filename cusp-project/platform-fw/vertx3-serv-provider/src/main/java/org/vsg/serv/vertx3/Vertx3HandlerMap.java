package org.vsg.serv.vertx3;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class Vertx3HandlerMap {
	
	
	private String path;
	
	private String method;
	
	public void invokeHandler(RoutingContext routingContext) {
        String productID = routingContext.request().getParam("productID");

        HttpServerResponse response = routingContext.response();
        if (productID == null) {
          sendError(400, response);
        } else {

        	response.putHeader("content-type", "text/plant").end("hello world");

        }		
	}

    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
      }	
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	

}
