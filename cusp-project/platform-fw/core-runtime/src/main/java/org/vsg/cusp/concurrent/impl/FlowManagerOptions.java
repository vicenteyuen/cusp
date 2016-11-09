package org.vsg.cusp.concurrent.impl;

import java.util.Collection;
import java.util.HashSet;

public class FlowManagerOptions {
	
	
	private boolean clustered = true;

	public FlowManagerOptions() {
		// TODO Auto-generated constructor stub
	}

	public boolean isClustered() {
		return clustered;
	}

	public void setClustered(boolean clustered) {
		this.clustered = clustered;
	}
	
	
	private Collection<String> scanPackages = new HashSet<String>();

	public Collection<String> getScanPackages() {
		return scanPackages;
	}

	public void setScanPackages(Collection<String> scanPackages) {
		this.scanPackages = scanPackages;
	}
	
	

	
	
}
