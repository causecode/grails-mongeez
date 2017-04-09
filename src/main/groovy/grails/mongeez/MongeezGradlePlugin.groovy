/*
 * Copyright (c) 2017, CauseCode Technologies Pvt Ltd, India.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are not permitted.
 */
package grails.mongeez

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * This Plugin's Gradle Plugin Descriptor file. It configures a custom copy task for copying migrations
 * to resources.
 * 
 * @author Nikhil Sharma
 * @since 0.2.4
 */
class MongeezGradlePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        // Create copy-mongeez-migrations task.
        Task copyMigrations = project.tasks.create(name: 'copy-mongeez-migrations', type: CopyMigrations)

        /*
         * Add dependency of this task to processResources task so that migrations are automatically copied before tasks
         * like grails run-app and grails war are run.
         */
        project.tasks.findByName("processResources").dependsOn(copyMigrations)

        // This block adds the compile dependency for this plugin so that it works as a Grails Plugin.
        project.buildscript.configurations.classpath.resolvedConfiguration.firstLevelModuleDependencies.each { module ->
            if (module.moduleGroup == 'com.causecode.plugins' && module.moduleName == 'grails-mongeez') {
                project.dependencies {
                    compile "${module.moduleGroup}:${module.moduleName}:${module.moduleVersion}"
                }
            }
        }
    }
}


