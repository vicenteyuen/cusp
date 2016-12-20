/**
 * 
 */
package org.vsg.cusp.plugins.apps.vo;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author ruanweibiao
 *
 */
public class CuspConfigration {
	
	private Platform platform = new Platform();
	

	
	
	
	public Platform getPlatform() {
		return platform;
	}


	public void setPlatform(Platform platform) {
		this.platform = platform;
	}


	private Set<MicroComp> microComps = new LinkedHashSet<MicroComp>();
	
	
	
	public Set<MicroComp> getMicroComps() {
		return microComps;
	}


	public void setMicroComps(Set<MicroComp> microComps) {
		this.microComps = microComps;
	}


	
	public static CuspConfigration getConfigrationInstance() {
		return new CuspConfigration();
	}

}
