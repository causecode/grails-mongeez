/*
 * Copyright (c) 2017, CauseCode Technologies Pvt Ltd, India.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
