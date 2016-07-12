package org.vsg.cusp.event.common;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vsg.cusp.concurrent.impl.FlowManagerOptions;
import org.vsg.cusp.event.flow.FlowManager;
import org.vsg.cusp.event.flow.Promise;
import org.vsg.cusp.event.flow.impl.FlowManagerImpl;
import org.vsg.cusp.event.flow.impl.ZmqEventBusImplEndPoint;
import org.vsg.cusp.event.impl.OperationEventMessageCodec;
import org.vsg.cusp.event.impl.PromiseProvider;
import org.vsg.cusp.event.impl.ZmqcmdHelper;
import org.vsg.cusp.eventbus.AsyncResult;
import org.vsg.cusp.eventbus.EventBus;
import org.vsg.cusp.eventbus.Handler;
import org.vsg.cusp.eventbus.impl.CodecManager;
import org.vsg.cusp.eventbus.impl.EventBusOptions;
import org.vsg.cusp.eventbus.impl.MessageExchangeEncoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class EventModule extends AbstractModule {

	private static Logger logger = LoggerFactory.getLogger(EventModule.class);	


	@Override
	protected void configure() {

		try {
			
			init();
			
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}
	
	private void init() throws IOException {
		// ---- load file  ---
		InputStream inputStream = this.getClass().getResourceAsStream("/conf/flow.json");
		
		JSONObject jsonConf = (JSONObject)JSON.parseObject(inputStream, JSONObject.class);
		
		String managerImpl = jsonConf.getString("impl");
		
		
		try {
			Class<FlowManager>  cls =  (Class<FlowManager>)Class.forName(managerImpl);
			
			FlowManagerOptions options = parseConfForManagerOptions(jsonConf);
			
			this.bind( FlowManagerOptions.class ).toInstance( options  );

			this.bind(FlowManager.class).to( FlowManagerImpl.class ).in( Scopes.SINGLETON );

			CodecManager codecManager = new CodecManager();
			codecManager.registerCodec(new OperationEventMessageCodec());
			this.bind(CodecManager.class).toInstance( codecManager );

			EventBusOptions ebOptions = parseConfForEventBus(jsonConf);
			this.bind( EventBusOptions.class ).toInstance( ebOptions );
			
			this.bind( EventBus.class ).to( ZmqEventBusImplEndPoint.class ).in(Scopes.SINGLETON);
			
			
			
			// --- reset promise ---
			this.bind( Promise.class ).toProvider( PromiseProvider.class );
			
			// --- operation event bus ---
			/*

			EventBusProvider eventBusProvider = new EventBusProvider();
			eventBusProvider.setOptions( ebOptions );
			this.bind( EventBus.class ).toProvider( eventBusProvider);
			
			
			// --- set promise ----
			this.bind(org.vsg.cusp.event.flow.Promise.class).toProvider(PromiseProvider.class);
			*/
			
		} catch (ClassNotFoundException | IllegalArgumentException |  SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * inject event bus option	
	 * @param flowManager
	 * @param options
	 */
	private void injectEventBus( EventBusOptions options) {
		
		
		ZmqEventBusImplEndPoint eventBusImpl = new ZmqEventBusImplEndPoint(options);
		
		/**
		 * handle define event
		 */
		Handler<AsyncResult<Void>> completionHandler = new Handler<AsyncResult<Void>>() {

			@Override
			public void handle(AsyncResult<Void> event) {
				// TODO Auto-generated method stub
				System.out.println(event);
			}
			
		};
		eventBusImpl.start(completionHandler);
		/*
		if (flowManager instanceof EventBusAware) {
			EventBusAware eba = (EventBusAware)flowManager;
			eba.setEventBus(eventBusImpl);
		}
		*/

	}
		
	
	private FlowManagerOptions parseConfForManagerOptions(JSONObject jsonConf) {
		FlowManagerOptions options = new FlowManagerOptions();
		
		JSONArray pkgArray = jsonConf.getJSONArray("scan-packages");
		for (int i = 0 ; i < pkgArray.size() ; i++) {
			options.getScanPackages().add( pkgArray.get(i).toString());
		}
		
		return options;
	}
	
	/**
	 * parse config options
	 * @param jsonConf
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private EventBusOptions parseConfForEventBus(JSONObject jsonConf) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		EventBusOptions options = new EventBusOptions();
		
		JSONObject eventBusConf =  jsonConf.getObject("event-bus", JSONObject.class);
		
		options.setImplClsName( eventBusConf.getString("impl") );
		
		JSONObject optionsConf = eventBusConf.getJSONObject("options");
		
		JSONArray  jsonArray = optionsConf.getJSONArray("senders");

		for (int i = 0 ; i < jsonArray.size() ; i++) {
			JSONObject senderConf = (JSONObject)jsonArray.get(i);
			
			StringBuilder hostStr = null;
			if (null != senderConf.getString("protocol") ) {
				hostStr = new StringBuilder(senderConf.getString("protocol"));
			} else {
				hostStr = new StringBuilder("tcp://");
			}
			hostStr.append( senderConf.getString("host") );
			hostStr.append( ":" ).append(senderConf.getIntValue("port"));
			options.getSenderHosts().add(hostStr.toString());
		}
		
		
		// --- parse encoder ---
		String encoderClsName = eventBusConf.getString("encoder");
		Class<MessageExchangeEncoder>  encoderCls =  (Class<MessageExchangeEncoder>)Class.forName(encoderClsName);
		MessageExchangeEncoder msgEncoder = encoderCls.newInstance();
		
		ZmqcmdHelper helper = new ZmqcmdHelper(options);
		helper.setEncoder( msgEncoder );
		

		return options;
	}	
	
}
