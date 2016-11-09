package org.vsg.deferred;

@FunctionalInterface
public interface Callback<E> {

	 void handle(E evtResult) throws java.lang.Throwable;
	  
}
