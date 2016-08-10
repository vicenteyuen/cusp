package org.vsg.cusp.event;

public interface Handler<E> {

	  void handle(E event);
	  
}
