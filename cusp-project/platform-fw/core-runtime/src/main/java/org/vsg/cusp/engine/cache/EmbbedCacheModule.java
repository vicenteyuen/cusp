package org.vsg.cusp.engine.cache;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.vsg.cusp.core.ServEngine;
import org.vsg.cusp.event.register.EhcacheRegisterEngine;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import java.util.Map;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.vsg.cusp.core.ServEngine;

import com.google.inject.AbstractModule;

public class EmbbedCacheModule extends AbstractModule {


	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		
		CacheManager cacheManager
	    = CacheManagerBuilder.newCacheManagerBuilder() 
	    .withCache("preConfigured",
	        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10))) 
	    .build();
		
		this.bind( CacheManager.class ).toInstance( cacheManager );

	}

}
