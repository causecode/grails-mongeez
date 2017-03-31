package grails.mongeez

import grails.core.GrailsApplication
import grails.plugins.Plugin
import org.mongeez.MongeezRunner

class MongeezGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.2.0 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
    ]
    def loadAfter = ['mongodb']

    def title = "mongeez" // Headline display name of the plugin
    def author = "David M. Carr"
    def authorEmail = "david@carrclan.us"
    def description = 'A plugin that integrates the Mongeez change management system for MongoDB into Grails.'
    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/mongeez"
    def license = "APACHE"
    def issueManagement = [ url: "https://github.com/commercehub-oss/grails-mongeez/issues" ]
    def scm = [ url: "https://github.com/commercehub-oss/grails-mongeez" ]
    
    GrailsApplication grailsApplication
    Closure doWithSpring() {
        { ->
            // TODO Implement runtime spring config (optional)
            def mongoBeanName = 'mongo'
            def databaseName = grailsApplication.config.grails.mongodb.databaseName
            def username = grailsApplication.config.grails.mongodb.username
            def password = grailsApplication.config.grails.mongodb.password

            def updateOnStart = grailsApplication.config.grails.mongeez?.updateOnStart ?: true

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
                changeSetFileProvider = ref('changeSetFileProvider')
            }
        }
    }
}
