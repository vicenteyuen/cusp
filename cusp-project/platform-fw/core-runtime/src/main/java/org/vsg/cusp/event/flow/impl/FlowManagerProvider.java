package org.vsg.cusp.event.flow.impl;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Provider;

import org.vsg.cusp.concurrent.impl.FlowManagerOptions;
import org.vsg.cusp.event.flow.FlowManager;


public class FlowManagerProvider implements Provider<FlowManager> {
	
	private Class<FlowManager> flowManagerInstCls;
	
	public Class<FlowManager> getFlowManagerInstCls() {
		return flowManagerInstCls;
	}

	public void setFlowManagerInstCls(Class<FlowManager> flowManagerInstCls) {
		this.flowManagerInstCls = flowManagerInstCls;
	}
	
	private FlowManagerOptions options;
	
	public FlowManagerOptions getOptions() {
		return options;
	}

	public void setOptions(FlowManagerOptions options) {
		this.options = options;
	}
	
	private static FlowManager _inst;

	@Override
	public FlowManager get() {
		// TODO Auto-generated method stub
		
		if (null ==_inst ) {
			try {
				_inst = flowManagerInstCls.getDeclaredConstructor(FlowManagerOptions.class).newInstance(options);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		return _inst;
	}

}
