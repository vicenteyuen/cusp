package org.vsg.cusp.platform.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceHelper {

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
					ServiceHelper.class.getClassLoader());
			if (factories.iterator().hasNext()) {
				return factories.iterator().next();
			} else {
				throw new IllegalStateException(
						"Cannot find META-INF/services/" + clazz.getName()
								+ " on classpath");
			}
		}
	}
	
	public static <T> T loadFactory(Class<T> clazz, ClassLoader parentClassLoader) {
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
					parentClassLoader);
			if (factories.iterator().hasNext()) {
				return factories.iterator().next();
			} else {
				throw new IllegalStateException(
						"Cannot find META-INF/services/" + clazz.getName()
								+ " on classpath");
			}
		}
	}
	
	public static <T> Collection<T> loadFactories(Class<T> clazz, ClassLoader parentClassLoader) {
		ServiceLoader<T> factories = ServiceLoader.load(clazz);
		
		Iterator<T>  factoriesIter =  factories.iterator();
		
		Collection<T> factoryColl = new ArrayList<T>();
		
		while (factoriesIter.hasNext()) {
			factoryColl.add( factoriesIter.next() );
		}
		
		// --- check class loader from ---
		if (factoryColl.isEmpty()) {
			factories = ServiceLoader.load(clazz,
					parentClassLoader);
			
			factoriesIter = factories.iterator();

			while (factoriesIter.hasNext()) {
				factoryColl.add( factoriesIter.next() );
			}		
		}
		
		
		if (factoryColl.isEmpty()) {
			throw new IllegalStateException(
					"Cannot find META-INF/services/" + clazz.getName()
							+ " on classpath");			
		}
		
		return factoryColl;

	}	

}
