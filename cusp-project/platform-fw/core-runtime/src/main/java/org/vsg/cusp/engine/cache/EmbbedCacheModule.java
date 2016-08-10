package org.vsg.cusp.engine.cache;

import java.net.URL;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.vsg.cusp.core.ServEngine;
import org.vsg.cusp.core.ServiceHolder;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

public class EmbbedCacheModule extends AbstractModule {


	@Override
	protected void configure() {
		
		
		URL customUrl  =  this.getClass().getClassLoader().getResource("META-INF/embbed-cache.xml");
		XmlConfiguration xmlConfig = new XmlConfiguration(customUrl);
		CacheManager cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
		/*
		CacheManager cacheManager
	    = CacheManagerBuilder.newCacheManagerBuilder() 
	    .withCache("preConfigured",
	        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10))) 
	    .build();
	    */
		
		this.bind( CacheManager.class ).toInstance( cacheManager );
		this.bind( ServEngine.class ).annotatedWith( Names.named( EhcacheRegisterEngine.class.getName())).to( EhcacheRegisterEngine.class ).in(Scopes.SINGLETON);
		

		this.bind(ServiceHolder.class).to( org.vsg.cusp.core.runtime.EngineCompLoaderServiceHolder.class ).in( Scopes.SINGLETON );
		

	}

}
