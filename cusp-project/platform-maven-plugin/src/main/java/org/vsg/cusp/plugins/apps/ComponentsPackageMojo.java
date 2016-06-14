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
import java.util.Set;
import java.util.Vector;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author Vison Ruan
 *
 */
@Mojo( name = "comps-package")
public class ComponentsPackageMojo extends AbstractAppMojo {

	
    @Parameter( defaultValue = "${project.artifacts}", required = true, readonly = true )
    private Set<Artifact> dependencies;	
    

    @Parameter(defaultValue = "${project.platformHome}",  required = false , readonly = true )   
    private File platformHome;
    
    @Parameter(defaultValue = "${run.debug}") 
    private boolean debug;
    
    @Parameter(defaultValue = "${project.basedir}")    
	private File basedir;
    

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		// TODO Auto-generated method stub

		System.out.println("hello message");

	}

		

}
