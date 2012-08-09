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

import net.lmxm.ute.event.JobStatusListener;
import org.apache.maven.plugin.logging.Log;

/**
 * This listener is responsible for receiving job status events such as job started and job stopped.
 */
public final class UtePluginJobStatusListener implements JobStatusListener {

    /**
     * Maven log handle used to output job status event messages.
     */
    private Log log;

    /**
     * Create a new listener instance that will use the provided log handle.
     *
     * @param log Maven log handle to use when relaying messages to the user.
     */
    public UtePluginJobStatusListener(Log log) {
        super();

        this.log = log;
    }

    /**
     * Executed when a job is aborted.
     */
    @Override
    public void jobAborted() {
        log.debug("Job aborted");
    }

    /**
     * Executed when a job completes successfully.
     */
    @Override
    public void jobCompleted() {
        log.debug("Job completed");
    }

    /**
     * Executed when a job is started.
     */
    @Override
    public void jobStarted() {
        log.debug("Job started");
    }

    /**
     * Executed when a job is stopped.
     */
    @Override
    public void jobStopped() {
        log.debug("Job stopped");
    }

    /**
     * Executed when a task is completed.
     */
    @Override
    public void jobTaskCompleted() {
        log.debug("Task completed");
    }

    /**
     * Executed when a task is skipped.
     */
    @Override
    public void jobTaskSkipped() {
        log.debug("Task skipped");
    }

    /**
     * Executed when a task is started.
     */
    @Override
    public void jobTaskStarted() {
        log.debug("Task started");
    }
}
