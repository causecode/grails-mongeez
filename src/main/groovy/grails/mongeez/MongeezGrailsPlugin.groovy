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
package grails.mongeez

import grails.core.GrailsApplication
import grails.plugins.Plugin
import groovy.util.logging.Slf4j
import org.mongeez.MongeezRunner
import org.mongeez.MongoAuth

@Slf4j
class MongeezGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.0.0 > *"

    def loadAfter = ['mongodb']

    def title = "mongeez" // Headline display name of the plugin
    def author = "David M. Carr"
    def authorEmail = "david@carrclan.us"
    def developers = [[name: 'Vishesh Duggar', email: 'vishesh@causecode.com'],
                      [name: 'Nikhil Sharma', email: 'nikhil.sharma@causecode.com'],
                      [name: 'Shivam Pandey', email: 'shivam.pandey@causecode.com']]
    def description = 'A plugin that integrates the Mongeez change management system for MongoDB into Grails.'
    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/mongeez"
    def license = "APACHE"
    def issueManagement = [ url: "https://github.com/commercehub-oss/grails-mongeez/issues" ]
    def scm = [ url: "https://github.com/commercehub-oss/grails-mongeez" ]

    GrailsApplication grailsApplication
    Closure doWithSpring() {
        // TODO Implement runtime spring config (optional)

        log.debug "Configuring Grails Mongeez Plugin..."

        String mongoBeanName = 'mongo'
        Map<String, Object> mongodbConfigurations = grailsApplication.config.grails.mongeezCreds ?:
                grailsApplication.config.grails.mongodb

        String databaseName = mongodbConfigurations.databaseName
        String username = mongodbConfigurations.username
        String password = mongodbConfigurations.password
        String authenticationDatabase = mongodbConfigurations.authenticationDatabase ?:
                mongodbConfigurations.databaseName

        boolean updateOnStart = grailsApplication.config.grails.mongeez?.updateOnStart ?: true

        return {
            mongeezService(MongeezService) { bean ->
                bean.scope = 'prototype'
                bean.autowire = 'byName'
                mongo = ref(mongoBeanName)
            }

            changeSetFileProvider(ReflectionsChangeSetFileProvider)

            mongeez(MongeezRunner) {
                executeEnabled = updateOnStart
                mongo = ref(mongoBeanName)
                dbName = databaseName
                userName = username
                passWord = password
                authDb = authenticationDatabase
                changeSetFileProvider = ref('changeSetFileProvider')
            }
        }
    }
}
