package org.vsg.serv.vertx3;

import io.vertx.core.AbstractVerticle;

import java.util.List;

import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.serv.api.ServiceProvider;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ServiceProviderBootstrap extends AbstractVerticle implements ServiceProvider {
	
	private static Logger logger = LoggerFactory.getLogger(AppServer.class);	
	
	private ClassLoader classLoader;
	
	public void setRuntimeClassLoader(ClassLoader classLoader)  {
		this.classLoader = classLoader;
	}
	

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
    	Injector injector = Guice.createInjector(new ControllerScanModule());
		
        //Router router = Router.router(vertx);		
		
        ControllerScanModule  modInst =  injector.getInstance(ControllerScanModule.class);
        
        
        List<Class<?>>  clses =  AnnotationReflectionUtils.findCandidates("org.vsg", Path.class);
        
        
        
        System.out.println("mod inst : " + clses);

	}


}
