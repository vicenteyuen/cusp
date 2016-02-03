/**
 * 
 */
package org.vsg.cusp.plugins.apps;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.vsg.cusp.bootstrap.BootstrapAgent;

/**
 * @author Vison Ruan
 *
 */
@Mojo( name = "run" )
public class AppRunMojo extends AbstractAppMojo {

	
    @Parameter( defaultValue = "${project.artifacts}", required = true, readonly = true )
    private Set<Artifact> dependencies;	
    

    @Parameter(defaultValue = "${project.platformHome}",  required = true , readonly = true )   
    private File platformHome;
    
    @Parameter(defaultValue = "${run.debug}") 
    private boolean debug;
    
    @Parameter(defaultValue = "${project.basedir}")    
	private File basedir;
    

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		// TODO Auto-generated method stub

		
        ClassLoader originalClassLoader = loadBootLibLoader(platformHome , Thread.currentThread().getContextClassLoader());
        
        originalClassLoader = loadLibLoader(platformHome , originalClassLoader);

		// ---create classloader handle ---
		
        try {
			
        	Class<?> bootCls = originalClassLoader.loadClass("org.vsg.cusp.bootstrap.BootstrapAgent");
			

			Method instanceMethod = bootCls.getMethod("getInstance");
			
			
			BootstrapAgent inst = (BootstrapAgent)instanceMethod.invoke(null);
			
			inst.setPlatformHome( platformHome );
			inst.setParentClassLoader(originalClassLoader);
			
			
			try {
				
				
				List<String> list = this.getProject().getRuntimeClasspathElements();
				
				inst.setRuntimeClasspathElements(list);
				
			} catch (DependencyResolutionRequiredException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			inst.startup(new String[]{});
			
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException(e.getLocalizedMessage());
		}

	}
	
	private ClassLoader loadBootLibLoader(File platformHome ,  ClassLoader parentClsLoader) {
		
		File bootLibs = new File( new File(platformHome , "bin") , "bootlib");
		
		File[] listJars = bootLibs.listFiles( new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				boolean isFile = pathname.isFile();
				
				if (!isFile) {
					
					return false;
				}
				
				boolean passed = pathname.getName().toLowerCase().endsWith(".jar");
				
				return passed;
			}

			
		});
		
		Vector<URL> classPaths = new Vector<URL>();
		
		ClassLoader currentClsLoader= parentClsLoader;
		
		try {
			
			for (File jarFile : listJars) {
				classPaths.add( jarFile.toURI().toURL() );
			}
			
			currentClsLoader = new URLClassLoader(classPaths.toArray(new URL[0]) , parentClsLoader);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return currentClsLoader;
		
		
		
	}
	
	private ClassLoader loadLibLoader(File platformHome ,  ClassLoader parentClsLoader) {
		
		File libs = new File(platformHome , "lib");
		
		File[] listJars = libs.listFiles( new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				boolean isFile = pathname.isFile();
				
				if (!isFile) {
					
					return false;
				}
				
				boolean passed = pathname.getName().toLowerCase().endsWith(".jar");
				
				return passed;
			}

			
		});
		
		Vector<URL> classPaths = new Vector<URL>();
		
		ClassLoader currentClsLoader= parentClsLoader;
		
		try {
			
			for (File jarFile : listJars) {
				classPaths.add( jarFile.toURI().toURL() );
			}
			System.out.println(classPaths);
			currentClsLoader = new URLClassLoader(classPaths.toArray(new URL[0]) , parentClsLoader);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return currentClsLoader;
		
		
		
	}
		

}
