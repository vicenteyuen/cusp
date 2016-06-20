/**
 * 
 */
package org.vsg.cusp.plugins.apps;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * @author Vison Ruan
 *
 */
@Mojo(name = "comps-package")
public class ComponentsPackageMojo extends AbstractAppMojo {

	@Parameter(defaultValue = "${project.artifacts}", required = true, readonly = true)
	private Set<Artifact> dependencies;

	@Parameter(defaultValue = "${project.platformHome}", required = false, readonly = true)
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
			this.getLog().error(
					"This project is not to under CUSP Components structure.");
			return;
		}

		// ---- check the maven define ---
		File micoCompsDir = checkAndCreateDir(platformHome, "mico-comps");

		// --- check share dir ---
		File shareDir = checkAndCreateDir(micoCompsDir, "share");

		// --- create base artifact dir ---
		File artifactDir = checkAndCreateDir(micoCompsDir,
				parentProject.getArtifactId());

		// --- filter self project ---
		List<MavenProject> subProjects = parentProject.getCollectedProjects();
		Iterator<MavenProject> subProjectIter = subProjects.iterator();
		while (subProjectIter.hasNext()) {
			MavenProject mp = subProjectIter.next();
			if (mp.getArtifactId().equals(this.getProject().getName())) {
				subProjectIter.remove();
			}
		}

		// --- update share component ---

		// --- get api and pub-runtime lib ---
		updateRuntimeLibsForShare(subProjects, shareDir);
		
		updateRuntimeLibsForPrivate(subProjects, artifactDir);

	}

	private File checkAndCreateDir(File parentFolder, String createComps) {
		File compsFolder = new File(parentFolder, createComps);

		if (!compsFolder.exists()) {
			compsFolder.mkdirs();
		}

		return compsFolder;
	}

	private void updateRuntimeLibsForShare(List<MavenProject> subProjects,
			File shareDir) {

		Collection<URI> allPubURL = new LinkedHashSet<URI>();

		// --- build subject project --
		for (MavenProject currentProj : subProjects) {
			String artifactId = currentProj.getArtifactId();
			if (artifactId.endsWith("biz-api")
					|| artifactId.endsWith("pub-runtime")) {
				try {

					Collection<URI> supportURIs = scanRuntimeDependencyLib(currentProj);

					Collection<URI> jarURIs =   scanPackageJarFile(currentProj);					
					
					File libs = checkAndCreateDir(shareDir , "lib");
					
					// --- copy lib file ---
					copyURLToFolder(supportURIs.toArray(new URI[0]) , libs);
					
					copyURLToFolder(jarURIs.toArray(new URI[0]) , libs);
					
				} catch (DependencyResolutionRequiredException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				continue;
			}

		}

	}
	
	private void updateRuntimeLibsForPrivate(List<MavenProject> subProjects,
			File privateFolder) {
		Collection<URI> allPubURL = new LinkedHashSet<URI>();

		// --- build subject project --
		for (MavenProject currentProj : subProjects) {
			String artifactId = currentProj.getArtifactId();
			if (artifactId.endsWith("pri-runtime")) {
				try {

					Collection<URI> supportURIs = scanRuntimeDependencyLib(currentProj);

					Collection<URI> jarURIs =   scanPackageJarFile(currentProj);					
					
					File libs = checkAndCreateDir(privateFolder , "lib");
					
					// --- copy lib file ---
					copyURLToFolder(supportURIs.toArray(new URI[0]) , libs);
					
					copyURLToFolder(jarURIs.toArray(new URI[0]) , libs);
					
				} catch (DependencyResolutionRequiredException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				continue;
			}

		}		
	}

	

	
	private Map<String,URI> orginalClasspathJar = new LinkedHashMap<String,URI>();

	private Collection<URI> scanRuntimeDependencyLib(MavenProject subProject)
			throws DependencyResolutionRequiredException {
		List<String> systemRuntimeClasspaths = subProject
				.getRuntimeClasspathElements();
		Collection<URI> allURLs = new LinkedHashSet<URI>();

		File classPathFile = null;
		for (String classpath : systemRuntimeClasspaths) {
			classPathFile = new File(classpath);
			
			// --- check contain handle ---
			String lastName = classPathFile.getName();
			
			// --- ignore classes path ---
			if (lastName.equalsIgnoreCase("classes")) {
				continue;
			}
			// --- check contain file path ---
			URI existedURI = orginalClasspathJar.get(lastName);
			
			if (null == existedURI) {
				if (classPathFile.exists()) {
					orginalClasspathJar.put(lastName, classPathFile.toURI());
					allURLs.add( classPathFile.toURI() );
				}				
			}

		}

		return allURLs;

	}

	
	private Collection<URI> scanPackageJarFile(MavenProject subProject) {
		
		String target = subProject.getBuild().getDirectory();
		
		File targetDir = new File(target);
		
		Collection<URI> vecs = new LinkedHashSet<URI>();
		
		if (targetDir.exists()) {
			
			File[] allJars = targetDir.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					// TODO Auto-generated method stub
					String fileName = pathname.getName();
					return fileName.endsWith(".jar");
				}
				
			});
			
			
			for (File jarFile : allJars) {
				
				URI bindingURI = this.orginalClasspathJar.get(jarFile.getName());
				
				if (null == bindingURI) {
					this.orginalClasspathJar.put( jarFile.getName() , jarFile.toURI());
					vecs.add( jarFile.toURI() );
				}
				
			}
			
		}
		return vecs;
	}
	
	private void copyURLToFolder(URI[] jarUrls, File containerFolder) {

		for (int i = 0; i < jarUrls.length; i++) {
			try {
				File sourceFile = new File(jarUrls[i]);
				Path source = Paths.get(jarUrls[i]);

				File targetFile = new File(containerFolder,
						sourceFile.getName());

				Path target = targetFile.toPath();

				if (!targetFile.exists()) {
					Files.copy(source, target,
							StandardCopyOption.COPY_ATTRIBUTES);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * clean all folder
	 * 
	 * @param containerFolder
	 */
	private void cleanForFolder(File containerFolder) {
		File[] listFiles = containerFolder.listFiles();

		try {
			for (File classPath : listFiles) {

				Files.deleteIfExists(classPath.toPath());

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
