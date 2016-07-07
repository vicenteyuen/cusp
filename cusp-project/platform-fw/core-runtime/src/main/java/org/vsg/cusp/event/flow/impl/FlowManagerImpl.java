package org.vsg.cusp.event.flow.impl;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.vsg.cusp.concurrent.EventFlow;
import org.vsg.cusp.concurrent.EventInfo;
import org.vsg.cusp.concurrent.OperationEvent;
import org.vsg.cusp.concurrent.impl.FlowManagerOptions;
import org.vsg.cusp.concurrent.impl.MultiNodeEventFlowImpl;
import org.vsg.cusp.concurrent.impl.OperationEventImpl;
import org.vsg.cusp.concurrent.impl.SingleNodeEventFlowImpl;
import org.vsg.cusp.core.utils.AnnotationReflectionUtils;
import org.vsg.cusp.event.flow.FlowManager;
import org.vsg.cusp.eventbus.AsyncResult;
import org.vsg.cusp.eventbus.EventBus;
import org.vsg.cusp.eventbus.EventBusAware;
import org.vsg.cusp.eventbus.Handler;
import org.vsg.cusp.eventbus.impl.EventBusOptions;

public class FlowManagerImpl implements FlowManager, EventBusAware {
	
	
	private Map<String, EventFlow> _efInstBinding = new LinkedHashMap<String , EventFlow>();
	
	
	private FlowManagerOptions flowManagerOptions;
	
	public FlowManagerImpl(FlowManagerOptions options) {
		flowManagerOptions = options;
	}
	
	private EventBus eventBus;

	@Override
	public void setEventBus(EventBus bus) {
		// TODO Auto-generated method stub
		this.eventBus = bus;
	}


	@Override
	public EventFlow getFlow(String flowId) {
		// TODO Auto-generated method stub
		EventFlow  eventFlow =  _efInstBinding.get(flowId);
		if (null == eventFlow) {
			
			if ( flowManagerOptions.isClustered() ) {
				// --- use multi node ---
				MultiNodeEventFlowImpl mnEventFlow = createMultiNodeEventFlow();
				eventFlow = mnEventFlow;
			}
			else {
				SingleNodeEventFlowImpl efi = new SingleNodeEventFlowImpl();
				efi.setFlowManager( this );
				eventFlow = efi;
			}
			_efInstBinding.put( flowId , eventFlow);
		}
		return eventFlow;
	}
	
	private MultiNodeEventFlowImpl createMultiNodeEventFlow() {
		MultiNodeEventFlowImpl mnEventFlow = new MultiNodeEventFlowImpl();
		mnEventFlow.setFlowManager( this );
		
		if ( mnEventFlow instanceof EventBusAware ) {
			((EventBusAware)mnEventFlow).setEventBus( eventBus );
		}
		
		
		
		/*
		EventFlowBroker venti = new EventFlowBroker();
		
		ExecutorService  es =  Executors.newSingleThreadExecutor();
		
		Future<Socket> future = es.submit( venti );
		
		
		try {
			System.out.println(future.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		es.shutdown();
		*/
		return mnEventFlow;
	}
	
	
	// --- init method ---
	public void init() {
		
		//List<Package> matchPackages =  scanPackageByRef("org.vsg");
		foundClsIncludingEventInfo("org.vsg");
		
	}
	
	
	private List<Package> scanPackageByRef(String containPackageName) {
		 Package[] packages = Package.getPackages();
		 
		 List<Package> allList = new LinkedList<Package>();
		 for (Package  pkg : packages) {
			 if (pkg.getName().startsWith(containPackageName)) {
				 allList.add( pkg );
			 }
		 }
		 
		 return allList;
	}
	
	
	private Map<String, OperationEventImpl> loadClassOperEvents = new LinkedHashMap<String , OperationEventImpl>();
	
	@Override
	public OperationEvent getOperEventById(String eventId) {
		return loadClassOperEvents.get(eventId);
	}
	
	private void foundClsIncludingEventInfo(String packagePath) {
		List<Package> matchPackages = scanPackageByRef(packagePath);
		
		for (Package candidatePackage : matchPackages) {
			List<Class<?>>  list = AnnotationReflectionUtils.findCandidates( candidatePackage.getName() , EventInfo.class );
			
			if (list.isEmpty()) {
				continue;
			}
			
			// --- get the annotation for class ---
			for (Class<?> cls : list) {
				Method[] declearMethods = cls.getMethods();
				
				for (Method method : declearMethods) {
					EventInfo  eveInfo =  method.getAnnotation(EventInfo.class);

					if (null != eveInfo) {
						
						// --- update method ---
						String shortId = eveInfo.id();
						String fullId = shortId + "@" + cls.getName();
						
						OperationEventImpl oeImpl =  loadClassOperEvents.get(fullId);
						OperationEventImpl shortOEImpl =  loadClassOperEvents.get(shortId);
						
						if (null == oeImpl) {
							oeImpl = new OperationEventImpl();
							oeImpl.setEventId( eveInfo.id() );
							oeImpl.setBindMethod( method);
							oeImpl.setAssoClassName( cls.getName() );
						}
						
						loadClassOperEvents.put( fullId , oeImpl);
					}

				}
				
			}

		}		
	}
	
	
	
	
	

}
