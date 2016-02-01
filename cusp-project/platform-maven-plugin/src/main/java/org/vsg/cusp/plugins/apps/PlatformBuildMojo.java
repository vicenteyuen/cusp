/**
 * 
 */
package org.vsg.cusp.plugins.apps;

import java.io.File;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * @author Vison Ruan
 *
 */
@Mojo( name = "build", threadSafe = true )
@Execute( phase = LifecyclePhase.PROCESS_CLASSES )
public class PlatformBuildMojo extends AbstractPlatformMojo {

	
    @Parameter( defaultValue = "${project.artifacts}", required = true, readonly = true )
    private Set<Artifact> dependencies;	
    
    @Parameter( defaultValue="${project.runtimeHome}" ,required = true)
    private File runtimeHome;
    

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		// TODO Auto-generated method stub

        
        
        
	}
		

}
