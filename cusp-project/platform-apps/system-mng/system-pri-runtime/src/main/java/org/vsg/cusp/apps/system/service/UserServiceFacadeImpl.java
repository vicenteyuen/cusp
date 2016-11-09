/**
 * 
 */
package org.vsg.cusp.apps.system.service;

import java.util.Map;

/**
 * @author vison
 *
 */
public class UserServiceFacadeImpl {
	
	// --- inject dao or service method ---
	
	public void saveUser(Map<?,?> beanMapping) {
		
		// --- call event handle ---
		
		System.out.println("call");
	}

}
