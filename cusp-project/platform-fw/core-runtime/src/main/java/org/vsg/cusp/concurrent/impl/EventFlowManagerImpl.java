package org.vsg.cusp.concurrent.impl;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.vsg.cusp.concurrent.EventFlow;
import org.vsg.cusp.concurrent.EventFlowManager;
import org.vsg.cusp.concurrent.EventInfo;
import org.vsg.cusp.concurrent.OperationEvent;
import org.vsg.cusp.core.utils.AnnotationReflectionUtils;

public class EventFlowManagerImpl implements EventFlowManager {
	
	
	private Map<String, EventFlow> _efInstBinding = new LinkedHashMap<String , EventFlow>();
	

	@Override
	public EventFlow getFlow(String flowId) {
		// TODO Auto-generated method stub
		EventFlow  eventFlow =  _efInstBinding.get(flowId);
		if (null == eventFlow) {
			EventFlowImpl efi = new EventFlowImpl();
			efi.setFlowManager( this );
			eventFlow = efi;
			_efInstBinding.put( flowId , eventFlow);
		}
		
		
		return eventFlow;
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
