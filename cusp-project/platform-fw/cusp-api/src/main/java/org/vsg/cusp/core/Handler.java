package org.vsg.cusp.core;

@FunctionalInterface
public interface Handler<E> {

	 void handle(E evtResult) throws java.lang.Throwable;
	  
}
