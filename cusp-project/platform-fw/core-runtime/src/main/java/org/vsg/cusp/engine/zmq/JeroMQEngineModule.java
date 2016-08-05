/**
 * 
 */
package org.vsg.cusp.engine.zmq;

import java.util.Map;
import java.util.concurrent.RunnableFuture;

import org.vsg.cusp.core.EventBusServEngine;
import org.vsg.cusp.core.ServEngine;
import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.MessageEncoder;
import org.vsg.cusp.event.impl.DefaultMessageExchangeEncoder;
import org.vsg.cusp.event.impl.MessageProvider;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;


/**
 * @author Vicente Yuen
 *
 */
public class JeroMQEngineModule extends AbstractModule {



	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		
		
		
		this.bind(Message.class).toProvider( MessageProvider.class );
		this.bind(MessageEncoder.class).to( DefaultMessageExchangeEncoder.class ).in(  Scopes.SINGLETON  );
		
		
		// --- set the service --
		this.bind(RunnableFuture.class).annotatedWith(Names.named("RequestResponseBroker")).to(ReqRepBroker.class).in( Scopes.SINGLETON );
		this.bind(RunnableFuture.class).annotatedWith(Names.named("RequestResponseWorker")).to(ReqRepWorker.class).in( Scopes.SINGLETON );
		
		
		// --- start mqbroker ---
		this.bind(EventBusServEngine.class).to( JeroMQServEngine.class ).in( Scopes.SINGLETON );

		
		
	}
	
	
	

	
	
}
