UTE Maven Plugin
================

Maven plugin wrapper for executing jobs using Universal Task Executer (UTE). This plugin makes it possible to execute
UTE jobs during any phase of a Maven build. Jobs may also be executed directly by invoking the ute:execute goal. By
utilizing UTE jobs developers may be able to setup and configure their applications for integration tests.

Note: This plugin is in the early development stages. Therefore it and it's dependencies (UTE) do not appear in any
public repositories. In order to use this plugin you must manually install or deploy this plugin and the associated
version of UTE.

Configuration
=============
The UTE plugin is configured through your project's pom file. Add a reference to the UTE plugin and provide an
appropriate configuration.

    <build>
        <plugins>
            ...
            <plugin>
                <groupId>net.lmxm.ute</groupId>
                <artifactId>ute-maven-plugin</artifactId>
                <version>0.1.0-SNAPSHOT</version>
                <configuration>
                    <inputFile>C:/UTE/MyJobs.ute</inputFile>
                    <jobIds>
                        <jobId>Create Configuration File Directories</jobId>
                    </jobIds>
                    <preferences>
                        <CONFIG_HOME>${project.basedir}/target/config</CONFIG_HOME>
                    </preferences>
                </configuration>
            </plugin>
            ...
        </plugins>
    </build>

<table>
<tr>
    <th>Option</th>
    <th>Required?</th>
    <th>Description</th>
</tr>
<tr>
    <td>inputFile</td>
    <td>Yes</td>
    <td>Path to the UTE configuration file. This may be an absolute or relative path. Relative paths are relative to the project base directory.</td>
</tr>
<tr>
    <td>jobIds</td>
    <td>Yes</td>
    <td>List of UTE job IDs to be executed. At least one job ID must be provided. Jobs are executed in the specified order.</td>
</tr>
<tr>
    <td>preferences</td>
    <td>No</td>
    <td>Name/Value pairs used to override the preference values loaded from the UTE preferences file.
</table>

Goals
=====

execute
-------

The execute goal is used to execute the UTE jobs specified in the plugin configuration. This goal is executed by
default during the "generate-resources" phase, but may also be executed manually: mvn ute:execute