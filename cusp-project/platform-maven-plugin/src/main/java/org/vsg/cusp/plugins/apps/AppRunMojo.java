/**
 * 
 */
package org.vsg.cusp.plugins.apps;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.vsg.cusp.bootstrap.BootstrapAgent;

/**
 * @author Vison Ruan
 *
 */
@Mojo( name = "run", requiresDependencyResolution = ResolutionScope.RUNTIME, threadSafe = true )
@Execute( phase = LifecyclePhase.PROCESS_CLASSES )
public class AppRunMojo extends AbstractAppMojo {

	
    @Parameter( defaultValue = "${project.artifacts}", required = true, readonly = true )
    private Set<Artifact> dependencies;	
    
    @Parameter( defaultValue="${project.runtimeHome}" ,required = true)
    private File runtimeHome;
    

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		// TODO Auto-generated method stub
		
		System.out.println(runtimeHome);

		// ---create classloader handle ---
		/*
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        
        BootstrapAgent serverAgent = BootstrapAgent.getInstance();
        
        // --- get all dependency jar---
        // --- set load ---
        serverAgent.setParentClassLoader( originalClassLoader );

        // --- and project class path element
        try {
			List<String> list = this.getProject().getRuntimeClasspathElements();
			
			serverAgent.setRuntimeClasspathElements(list);


		} catch (DependencyResolutionRequiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        serverAgent.startup(new String[]{});
        */
        
        
        
        
	}
		

}
