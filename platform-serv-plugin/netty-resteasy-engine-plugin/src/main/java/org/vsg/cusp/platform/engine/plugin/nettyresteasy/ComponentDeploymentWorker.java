package org.vsg.cusp.platform.engine.plugin.nettyresteasy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ws.rs.Path;

import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.vsg.cusp.platform.AnnotationReflectionUtils;
import org.vsg.cusp.platform.runtime.GuiceInjectorAware;

import com.google.inject.Injector;
import com.google.inject.Module;

public class ComponentDeploymentWorker implements Runnable , GuiceInjectorAware {
	
	
	private ResteasyDeployment resteasyDeployment;
	
	private ClassLoader classLoader;

	private File compBaseDir;
	
	public ResteasyDeployment getResteasyDeployment() {
		return resteasyDeployment;
	}

	public void setResteasyDeployment(ResteasyDeployment resteasyDeployment) {
		this.resteasyDeployment = resteasyDeployment;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public File getCompConfDir() {
		return compBaseDir;
	}

	public void setCompConfDir(File compConfDir) {
		this.compBaseDir = compConfDir;
	}
	
	private Injector parentInjector;


	@Override
	public void setParentInjector(Injector injector) {
		this.parentInjector = injector;
	}

	@Override
	public void run() {
		
		initProperties();
		
		// --- scan package ---
		List<Class<?>> allPathClzz = scanPackage();
		
		Module[] modules = new Module[0];
		
		Injector componentInjector = parentInjector.createChildInjector(modules);
		

		// ---- get class instnace ---
		List<Object> resourceList = new Vector<Object>();
		for (Class<?> pathClz : allPathClzz) {
			Object obj = componentInjector.getInstance( pathClz );
			resourceList.add( obj );
		}
		
		// --- set the context  ---
		Registry registry = getResteasyDeployment().getRegistry();

		// --- content path context ---
		String basePath = props.getProperty("path.ctx", "/");

		for (Object resource : resourceList) {
			registry.addSingletonResource(resource, basePath);		
		}


		// ---- add to resteasy deployment ---





	}
	
	private List<Class<?>> scanPackage() {
		Thread.currentThread().setContextClassLoader( this.classLoader );
		
		String packagesScan = this.getProps().get("package.scan").toString();
		int multiPackageLoc = packagesScan.indexOf(",");
		Set<String> packages = new HashSet<String>();
		if (multiPackageLoc > -1) {
			StringTokenizer st = new StringTokenizer(packagesScan, ",");
			while (st.hasMoreTokens()) {
				packages.add( st.nextToken() );
			}
		}
		else {
			packages.add( packagesScan );
		}
		
		List<Class<?>> allPathClzz = new Vector<Class<?>>();
		for (String basePackage : packages.toArray(new String[0])) {
			List<Class<?>>  pathClzList =  AnnotationReflectionUtils.findCandidates(basePackage, Path.class);
			allPathClzz.addAll( pathClzList );
		}
		
		return allPathClzz;

	}
	
	
	
	private void initProperties() {
		File config = new File(new File(compBaseDir,"conf") , "netty-resteasy.cfg");
		FileInputStream fis = null;		
		try {
			
			if (!config.exists()) {
				return;
			}			
			fis = new FileInputStream(config);
			
			props.load(fis);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if (null != fis) {
					fis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				fis = null;
			}
			
		}
	}

	
	private Properties props = new Properties() ;

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}
			
			

}
