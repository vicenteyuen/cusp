/**
 * 
 */
package org.vsg.cusp.event;

import java.util.Set;

/**
 * @author Vicente Yuen
 *
 */
public interface EventMethodRegister {

	/**
	 * register event method to cache storage
	 * @param eventId
	 * @param methodDescription
	 */
	void registerEvent(String eventName , EventMethodDescription methodDescription);
	
	
	/**
	 * return all register event handle
	 * @param eventId
	 * @return
	 */
	Set<EventMethodDescription> findAllRegisterEventsByName(String eventName);
	
	/**
	 * unregister all event methind binding event 
	 * @param eventId
	 */
	void unRegisterEvent(String eventName);
}
