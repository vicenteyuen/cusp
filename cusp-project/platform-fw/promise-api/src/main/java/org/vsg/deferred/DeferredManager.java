package org.vsg.deferred;

import java.util.ServiceLoader;




public interface DeferredManager {
	
	<D> Promise<D, Throwable, Void> when(Callback<D>... futures);

	/**
	 * define handle
	 * @param handler
	 * @return
	 */
	<D> Promise<D, Throwable, Void> when(Callback<D> handler);
	

	<D> Promise<D, Throwable, Void> succeed(Callback<D> succeedHandler);
	
	
	<D> Promise<D, Throwable, Void> fail(Callback<D> failHandler);
	
	
	public static <T> T loadFactory(Class<T> clazz) {
		ServiceLoader<T> factories = ServiceLoader.load(clazz);
		if (factories.iterator().hasNext()) {
			return factories.iterator().next();
		} else {
			// By default ServiceLoader.load uses the TCCL, this may not be
			// enough in environment deading with
			// classloaders differently such as OSGi. So we should try to use
			// the classloader having loaded this
			// class. In OSGi it would be the bundle exposing vert.x and so have
			// access to all its classes.
			factories = ServiceLoader.load(clazz,
					DeferredManager.class.getClassLoader());
			if (factories.iterator().hasNext()) {
				return factories.iterator().next();
			} else {
				throw new IllegalStateException(
						"Cannot find META-INF/services/" + clazz.getName()
								+ " on classpath");
			}
		}
	}	
}
