/*
 * Copyright 2012 David M. Carr, Commerce Technologies
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

// Make the migrations available on the classpath when using run-app
eventPackagingEnd = {
    def srcPath = config.grails?.mongeez?.changelogLocation ?: 'grails-app/migrations'
    def src = new File(buildSettings.baseDir, srcPath)
    def target = new File(buildSettings.resourcesDir, 'migrations')
    if (src.exists()) {
        ant.sync(toDir: target, overwrite: true) {
            fileset(dir: src, includes: "**/*.xml,**/*.js")
        }
    }
}

// Make the migrations available on the classpath when packaged as a WAR
eventCreateWarStart = { name, stagingDir ->
    def srcPath = config.grails?.mongeez?.changelogLocation ?: 'grails-app/migrations'
    def src = new File(buildSettings.baseDir, srcPath)
    def target = new File(stagingDir, 'WEB-INF/classes/migrations')
    if (src.exists()) {
        ant.sync(toDir: target, overwrite: true) {
            fileset(dir: src, includes: "**/*.xml,**/*.js")
        }
    }
}
