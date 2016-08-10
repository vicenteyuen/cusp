package org.vsg.cusp.concurrent;

public interface Handler<E> {

	  void handle(E event);
	  
}
