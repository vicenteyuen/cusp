package org.vsg.serv.vertx3;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.serv.api.ServiceProvider;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ServiceProviderBootstrap extends AbstractVerticle implements ServiceProvider {
	
	private static Logger logger = LoggerFactory.getLogger(AppServer.class);	
	
	private ClassLoader runtimeClassLoader;
	
	public void setRuntimeClassLoader(ClassLoader runtimeClassLoader)  {
		this.runtimeClassLoader = runtimeClassLoader;
	}
	

	@Override
	public void start() {
		// TODO Auto-generated method stub
		Set<String> scanPackages = new LinkedHashSet<String>();
		scanPackages.add("org.vsg");
		

        Router router = Router.router(vertx);			
		
    	Injector injector = Guice.createInjector(
    			new JsrRestControllerModule(router , runtimeClassLoader , scanPackages));
	
		
        //JsrRestControllerModule  modInst =  injector.getInstance(JsrRestControllerModule.class);
        

        


        
        //List<Class<?>>  clses  =  rcar.findCandidates("org.vsg", Path.class);

        
        

        
        
        //System.out.println("mod inst : " + clses);

	}


}
