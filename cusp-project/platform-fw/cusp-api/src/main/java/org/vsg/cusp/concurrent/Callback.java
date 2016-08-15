package org.vsg.cusp.concurrent;

/**
 * call back handle
 * @author ruanweibiao
 *
 * @param <T>
 */
@FunctionalInterface
public interface Callback<T> {

	void onDone(T result, Throwable error) throws Exception;	
	
}
