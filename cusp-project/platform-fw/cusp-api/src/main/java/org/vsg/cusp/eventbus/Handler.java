package org.vsg.cusp.eventbus;

public interface Handler<E> {

	  void handle(E event);
	  
}
