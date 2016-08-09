/**
 * 
 */
package org.vsg.cusp.engine.zmq;

import java.io.IOException;
import java.io.InputStream;

import org.vsg.cusp.core.ServEngine;
import org.vsg.cusp.event.EventTrigger;
import org.vsg.cusp.event.Message;
import org.vsg.cusp.event.MessageEncoder;
import org.vsg.cusp.event.impl.DefaultMessageExchangeEncoder;
import org.vsg.cusp.event.impl.EventTriggerImpl;
import org.vsg.cusp.event.impl.MessageProvider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;


/**
 * @author Vicente Yuen
 *
 */
public class EventBusMasterEngineModule extends AbstractModule {



	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		
		
		// --- define pre handle object ---
		this.bind(Message.class).toProvider( MessageProvider.class );
		this.bind(MessageEncoder.class).to( DefaultMessageExchangeEncoder.class ).in(  Scopes.SINGLETON  );
		
		
		// --- set the service --
		this.bind(Runnable.class).annotatedWith(Names.named("RequestResponseBroker")).to(ReqRepBroker.class).in( Scopes.SINGLETON );
		this.bind(Runnable.class).annotatedWith(Names.named("RequestResponseWorker")).to(ReqRepWorker.class).in( Scopes.SINGLETON );
		
		
		// --- start mqbroker and master worker ---
		this.bind( ServEngine.class ).annotatedWith( Names.named( MasterEventBusServEngine.class.getName())).to( MasterEventBusServEngine.class ).in(Scopes.SINGLETON);
		

		bindClientEndpoint();
	}

	
	private void bindClientEndpoint() {
	
		// ---- load file  ---
		InputStream inputStream = this.getClass().getResourceAsStream("/conf/flow.json");
		
		try {
			
			JSONObject jsonConf = (JSONObject)JSON.parseObject(inputStream, JSONObject.class);
			String managerImpl = jsonConf.getString("impl");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.bind(EventTrigger.class ).to( EventTriggerImpl.class ).in( Scopes.SINGLETON );
		
		
	}

	
	
}
