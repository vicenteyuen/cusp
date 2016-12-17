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
	
	
	private Set<MicroComp> microcomps = new LinkedHashSet<MicroComp>();
	
	
	
	
	
	
	private class Platform {
		
		private String configrationPath;
		
		
	}
	
	
	private class MicroComp {
		
		private String configationPath;
		
	}
	
	
	public static CuspConfigration getConfigrationInstance() {
		return new CuspConfigration();
	}

}
