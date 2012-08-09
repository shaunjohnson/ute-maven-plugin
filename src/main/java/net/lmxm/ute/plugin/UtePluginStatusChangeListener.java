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

import net.lmxm.ute.event.StatusChangeEvent;
import net.lmxm.ute.event.StatusChangeEventType;
import net.lmxm.ute.event.StatusChangeListener;
import org.apache.maven.plugin.logging.Log;

/**
 * This listener is responsible for receiving status change events such as info or error messages. Each status change
 * event received is outputted to the user via the Maven log handle.
 */
public final class UtePluginStatusChangeListener implements StatusChangeListener {

    /**
     * Maven log handle used to output status change event messages.
     */
    private Log log;

    /**
     * Create a new listener instance that will use the provided log handle.
     *
     * @param log Maven log handle to use when relaying messages to the user.
     */
    public UtePluginStatusChangeListener(Log log) {
        super();

        this.log = log;
    }

    /**
     * Handles a status change event by sending the appropriate message to the Maven log handle.
     *
     * @param statusChangeEvent Information about the event that occurred.
     */
    @Override
    public void statusChange(StatusChangeEvent statusChangeEvent) {
        final StatusChangeEventType eventType = statusChangeEvent.getEventType();
        final String message = statusChangeEvent.getMessage();

        if (eventType == StatusChangeEventType.INFO) {
            log.info(message);
        }
        else if (eventType == StatusChangeEventType.HEADING) {
            log.info("== " + message + " ==");
        }
        else if (eventType == StatusChangeEventType.IMPORTANT) {
            log.info(">>>> " + message + " <<<<");
        }
        else if (eventType == StatusChangeEventType.ERROR || eventType == StatusChangeEventType.FATAL) {
            log.error(message);
        }
        else {
            log.error("statusChange() : unsupported event type: " + eventType);
        }
    }
}
