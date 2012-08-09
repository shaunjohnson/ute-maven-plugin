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

import net.lmxm.ute.GenericApplication;
import net.lmxm.ute.beans.Preference;
import net.lmxm.ute.beans.configuration.Configuration;
import net.lmxm.ute.beans.jobs.Job;
import net.lmxm.ute.configuration.ConfigurationReader;
import net.lmxm.ute.event.JobStatusListener;
import net.lmxm.ute.event.StatusChangeListener;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * UTE application implementation used to execute UTE jobs from this plugin.
 */
public final class UtePluginApplication extends GenericApplication {

    /**
     * UTE input file used as a source of jobs.
     */
    private final File inputFile;

    /**
     * ID of the job to be executed.
     */
    private final String jobId;

    /**
     * Job status listener used to handle job status events during job execution.
     */
    private final JobStatusListener jobStatusListener;

    /**
     * Maven log handle for reporting messages to the user.
     */
    private final Log log;

    /**
     * Map of preference names and values used to override the preferences loaded from disk.
     */
    private final List<Preference> preferences;

    /**
     * Status change listener used to handle status change events during job execution.
     */
    private final StatusChangeListener statusChangeListener;

    /**
     * Create a new UTE application object used for executing UTE jobs.
     *
     * @param log            Maven log handle to use for reporting messages to the user.
     * @param inputFile      UTE input file to load.
     * @param jobId          ID of job to be executed.
     * @param preferencesMap Map of preferences used to override the preferences loaded from disk.
     */
    public UtePluginApplication(Log log, File inputFile, String jobId, Map<String, String> preferencesMap) {
        super();

        this.inputFile = inputFile;
        this.jobId = jobId;
        this.log = log;
        this.preferences = convertMapToPreferences(preferencesMap);
        this.jobStatusListener = new UtePluginJobStatusListener(log);
        this.statusChangeListener = new UtePluginStatusChangeListener(log);
    }

    /**
     * Converts a Map of String keys and values into a List of Preference objects.
     *
     * @param preferencesMap Map to convert to preferences
     * @return List of Preference objects.
     */
    private List<Preference> convertMapToPreferences(Map<String, String> preferencesMap) {
        final List<Preference> preferenceList = new ArrayList<Preference>();

        if (preferencesMap != null) {
            for (Map.Entry<String, String> entry : preferencesMap.entrySet()) {
                preferenceList.add(new Preference(entry.getKey(), entry.getValue()));
            }
        }

        return preferenceList;
    }

    /**
     * Execute the identified job using the provided input file and preferences. All output of the job execution is
     * redirected to the Maven log handle.
     */
    public void execute() {
        log.debug("execute() : entered");

        final Configuration configuration = new ConfigurationReader(inputFile).read();
        loadAndValidatePreferencesAreSet(configuration, preferences);

        final List<Job> jobs = loadJobs(configuration, jobId, null);
        executeJobs(jobs, configuration, jobStatusListener, statusChangeListener);

        log.debug("execute() : leaving");
    }
}
