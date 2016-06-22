/**
 * 
 */
package org.vsg.cusp.core.rapidoid.eventrest;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Link.Builder;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant.VariantListBuilder;
import javax.ws.rs.ext.RuntimeDelegate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Vicente Yuen
 *
 */
public class RapidoidRuntimeDelegate extends RuntimeDelegate {
	
	
	private static Logger logger = LoggerFactory.getLogger( RapidoidRuntimeDelegate.class );

	/* (non-Javadoc)
	 * @see javax.ws.rs.ext.RuntimeDelegate#createUriBuilder()
	 */
	@Override
	public UriBuilder createUriBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.ext.RuntimeDelegate#createResponseBuilder()
	 */
	@Override
	public ResponseBuilder createResponseBuilder() {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug("create response builder : ");
		}
		
		return new ResponseBuilderImpl();
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.ext.RuntimeDelegate#createVariantListBuilder()
	 */
	@Override
	public VariantListBuilder createVariantListBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.ext.RuntimeDelegate#createEndpoint(javax.ws.rs.core.Application, java.lang.Class)
	 */
	@Override
	public <T> T createEndpoint(Application application, Class<T> endpointType)
			throws IllegalArgumentException, UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.ext.RuntimeDelegate#createHeaderDelegate(java.lang.Class)
	 */
	@Override
	public <T> HeaderDelegate<T> createHeaderDelegate(Class<T> type)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.ext.RuntimeDelegate#createLinkBuilder()
	 */
	@Override
	public Builder createLinkBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

}
