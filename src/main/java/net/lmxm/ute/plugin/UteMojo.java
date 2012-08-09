/**
 * Copyright (C) 2012 Shaun Johnson, LMXM LLC
 *
 * This file is part of UTE Maven Plugin.
 *
 * UTE Maven Plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * UTE Maven Plugin is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * UTE Maven Plugin. If not, see <http://www.gnu.org/licenses/>.
 */
package net.lmxm.ute.plugin;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;

import java.io.File;
import java.util.Map;

/**
 * UTE execute goal implementation. This goal is designed to execute UTE jobs.
 */
@Mojo(name = "execute", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public final class UteMojo extends AbstractMojo {

    /**
     * UTE configuration file.
     */
    @Parameter(required = true)
    private File inputFile;

    /**
     * UTE job IDs to be executed.
     */
    @Parameter(required = true)
    private String[] jobIds;

    /**
     * The Maven Project.
     */
    @Parameter(required = true, readonly = true, defaultValue = "${project}")
    protected MavenProject project;

    /**
     * Local Repository.
     */
    @Parameter(required = true, readonly = true, defaultValue = "${localRepository}")
    protected ArtifactRepository localRepository;

    /**
     * The Maven Settings.
     */
    @Parameter(required = true, readonly = true, defaultValue = "${settings}")
    private Settings settings;

    /**
     * UTE preference override values.
     */
    @Parameter
    private Map<String, String> preferences;

    /**
     * Execute a UTE job goal.
     *
     * @throws MojoExecutionException
     */
    public void execute() throws MojoExecutionException {
        getLog().info("Starting execution");

        for (String jobId : jobIds) {
            new UtePluginApplication(getLog(), convertFileToAbsolutePath(inputFile), jobId, preferences).execute();
        }

        getLog().info("Execution complete");
    }

    /**
     * Converts the provided file to an absolute file based on the project base directory.
     *
     * @param file File to be converted to an absolute file path
     * @return Absolute file path of the input file
     */
    private File convertFileToAbsolutePath(File file) {
        return file.isAbsolute() ? file : new File(project.getBasedir(), file.toString());
    }
}
