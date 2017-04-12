/*
 * Copyright (c) 2017, CauseCode Technologies Pvt Ltd, India.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are not permitted.
 */
package grails.mongeez

import org.gradle.api.tasks.Copy

/**
 * A Custom task for Copying the `grails-app/migrations` directory to resources.
 *
 * @author Nikhil Sharma
 * @since 0.2.4
 */
class CopyMigrations extends Copy {

    CopyMigrations() {
        // Create migrations directory.
        File destDir = new File("${project.buildDir}/resources/main", 'migrations')
        if (!destDir.exists()) {
            destDir.mkdir()
        }

        // Include only JavaScript and XML files.
        include '**/*.js', '**/*.xml'

        from "grails-app/migrations"
        into "${project.buildDir}/resources/main/migrations"
    }
}
