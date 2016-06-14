/**
 * 
 */
package org.vsg.cusp.plugins.apps;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

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
		// --- get the parent project ---
		MavenProject parentProject = this.getProject().getParent();
		
		if (null == parentProject) {
			this.getLog().error("This project is not to under CUSP Components structure.");
			return;
		}
		
		
		/*
		List<String> modules = parentProject.getModules();
		Iterator<String> moduleIter = modules.iterator();
		while (moduleIter.hasNext()) {
			String currentName = moduleIter.next();
			if (currentName.equals( this.getProject().getName() )) {
				moduleIter.remove();
			}
		}*/
		
		List<MavenProject> subProjects =  parentProject.getCollectedProjects();
		Iterator<MavenProject> subProjectIter = subProjects.iterator();
		while (subProjectIter.hasNext()) {
			MavenProject mp = subProjectIter.next();
			if (mp.getArtifactId().equals( this.getProject().getName() )) {
				subProjectIter.remove();
			}
		}
		
		System.out.println(subProjects);
		

		
		//parentProject
		

		



	}

		

}
