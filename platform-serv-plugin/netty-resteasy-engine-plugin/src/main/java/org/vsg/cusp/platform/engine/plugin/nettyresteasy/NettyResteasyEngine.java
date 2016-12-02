/**
 * 
 */
package org.vsg.cusp.platform.engine.plugin.nettyresteasy;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.common.async.AsyncResult;
import org.vsg.common.async.Callback;
import org.vsg.common.async.DefaultAsyncResult;
import org.vsg.cusp.platform.api.Container;
import org.vsg.cusp.platform.api.LifecycleState;
import org.vsg.cusp.platform.core.api.ContainerAware;
import org.vsg.cusp.platform.core.api.ServEngine;
import org.vsg.cusp.platform.core.api.ServEngineInfo;
import org.vsg.cusp.platform.core.api.StartException;
import org.vsg.cusp.platform.runtime.GuiceInjectorAware;

import com.google.inject.Injector;

/**
 * define handle 
 * @author Vicente Ruan
 *
 */
@ServEngineInfo(name="netty-resteasy")
public class NettyResteasyEngine implements ServEngine , ContainerAware , GuiceInjectorAware{
	
	private static Logger logger = LoggerFactory.getLogger(NettyResteasyEngine.class);	
	
	
	private NettyJaxrsServer nettyServer;
	
	
	private Container container;
	

	@Override
	public void setContainer(Container container) {
		// TODO Auto-generated method stub
		this.container = container;
	}
	
	private Injector parentInjector;
	
	@Override
	public void setParentInjector(Injector injector) {
		parentInjector = injector;
	}



	/* (non-Javadoc)
	 * @see org.vsg.cusp.platform.core.api.Service#start(org.vsg.common.async.Callback)
	 */
	@Override
	public <ServEngine> void start(Callback<AsyncResult<ServEngine>> callback) throws StartException {
		
		nettyServer = new NettyJaxrsServer();
		int port = arguments.get("engine.port") != null ? Integer.parseInt( arguments.get("engine.port") ) : 8808;
		nettyServer.setPort(port);	
		// --- scan and deploy package
		ExecutorService exec = Executors.newFixedThreadPool( 5 );


		nettyServer.setDeployment( this.red );
		nettyServer.start();		

		
		for (ComponentDeploymentWorker cdw : compsWorkers) {
			
			GuiceInjectorAware bean = (GuiceInjectorAware)cdw;
			bean.setParentInjector( this.parentInjector );
			
			exec.execute( cdw );
		}				
		
		if (logger.isInfoEnabled()) {
			logger.info("Start \"netty-resteasy\" at port " + port);
		}
		

		// --- return  call back ---
		DefaultAsyncResult<ServEngine> dar = new DefaultAsyncResult<ServEngine>();
		dar.setSucceeded(true);
		try {
			
			dar.setResult((ServEngine)this);
			if (null != callback) {
				callback.invoke(dar);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		



	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.platform.core.api.Service#stop(org.vsg.common.async.Callback)
	 */
	@Override
	public <T> void stop(Callback<AsyncResult<T>> callback) {
		
		if (null != nettyServer) {
			nettyServer.stop();
		}


	}
	
	private ResteasyDeployment red;
	
	private List<ComponentDeploymentWorker> compsWorkers = new Vector<ComponentDeploymentWorker>();

	/* (non-Javadoc)
	 * @see org.vsg.cusp.platform.core.api.ServEngine#init(java.util.Map, org.vsg.common.async.Callback)
	 */
	@Override
	public <ServEngine> void init(Map<String, String> arguments, Callback<AsyncResult<ServEngine>> callback) {
		
		if (logger.isInfoEnabled()) {
			logger.info("Init Netty Resteasy engine.");
		}
		
		this.arguments = arguments;

		
		
		//ResteasyProviderFactory providerFactory = new ResteasyProviderFactory();
		//providerFactory.setBuiltinsRegistered(true);
		
		// --- scan path ---
		red = new ResteasyDeployment();
		//red.setProviderFactory( providerFactory );

		red.setAsyncJobServiceEnabled(true);
		red.setAsyncJobServiceBasePath("/");
		red.setAsyncJobServiceThreadPoolSize(50);

		
		
		
		Map<String,File>  compDirs = container.getComponentsPath();
		Map<String, ClassLoader> compLoaders =  container.getComponentsClassLoader();

		for (Entry<String, ClassLoader> compLoaderList : compLoaders.entrySet()) {
			//genResourceInstnace(compLoaderList.getValue());
			File confDir = compDirs.get( compLoaderList.getKey() );
			
			ComponentDeploymentWorker worker = new ComponentDeploymentWorker();
			worker.setResteasyDeployment( red );
			worker.setClassLoader( compLoaderList.getValue() );
			worker.setCompConfDir( confDir );
			
			compsWorkers.add(worker);

		}
		

		
		DefaultAsyncResult<ServEngine> dar = new DefaultAsyncResult<ServEngine>();
		dar.setSucceeded(true);
		try {
			
			dar.setResult((ServEngine)this);
			if (null != callback) {
				callback.invoke(dar);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	private Map<String,String> arguments;

	/* (non-Javadoc)
	 * @see org.vsg.cusp.platform.core.api.ServEngine#getState()
	 */
	@Override
	public LifecycleState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.platform.core.api.ServEngine#setState(org.vsg.cusp.platform.api.LifecycleState)
	 */
	@Override
	public void setState(LifecycleState newState) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.vsg.cusp.platform.core.api.ServEngine#destory(org.vsg.common.async.Callback)
	 */
	@Override
	public <T> void destory(Callback<AsyncResult<T>> callback) {
		// TODO Auto-generated method stub

	}

}
