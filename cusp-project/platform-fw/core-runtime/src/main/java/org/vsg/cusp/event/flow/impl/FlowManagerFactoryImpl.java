/**
 * 
 */
package org.vsg.cusp.event.flow.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.vsg.cusp.concurrent.impl.FlowManagerOptions;
import org.vsg.cusp.event.flow.FlowManager;
import org.vsg.cusp.event.flow.FlowManagerFactory;
import org.vsg.cusp.event.impl.ZmqcmdHelper;
import org.vsg.cusp.eventbus.AsyncResult;
import org.vsg.cusp.eventbus.EventBusAware;
import org.vsg.cusp.eventbus.Handler;
import org.vsg.cusp.eventbus.impl.EventBusOptions;
import org.vsg.cusp.eventbus.impl.MessageExchangeEncoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Vicente Yuen
 *
 */
public class FlowManagerFactoryImpl implements FlowManagerFactory {

	
	public FlowManagerFactoryImpl() {
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Map<String,Object> params = new LinkedHashMap<String,Object>();
	
	
	private void init() throws IOException {
		// ---- load file  ---
		InputStream inputStream = this.getClass().getResourceAsStream("/conf/flow.json");
		
		JSONObject jsonConf = (JSONObject)JSON.parseObject(inputStream, JSONObject.class);
		
		String managerImpl = jsonConf.getString("impl");
		
		
		try {
			Class<FlowManager>  cls =  (Class<FlowManager>)Class.forName(managerImpl);
			
			FlowManagerOptions options = parseConfForManagerOptions(jsonConf);
			
			flowManager = cls.getDeclaredConstructor(FlowManagerOptions.class).newInstance(options);
			
			//EventBusOptions ebOptions = parseConfForEventBus(jsonConf);
			//injectEventBus(flowManager , ebOptions);
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * inject event bus option	
	 * @param flowManager
	 * @param options
	 */
	private void injectEventBus(FlowManager flowManager , EventBusOptions options) {
		
		
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
		
		if (flowManager instanceof EventBusAware) {
			EventBusAware eba = (EventBusAware)flowManager;
			eba.setEventBus(eventBusImpl);
		}

	}
	
	
	
	private FlowManager flowManager ;
	
	@Override
	public FlowManager getManager() {
		// TODO Auto-generated method stub
		return flowManager;
	}

	
	
}
