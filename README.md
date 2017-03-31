# Grails MongoDB Database Migration Plugin

## Overview 

This MongoDB database migration plugin helps to manage database changes while developing the grails 3.x applications and MongoDB as database. This plugin uses the [mongeez](https://github.com/mongeez/mongeez) library and change sets written in JavaScript. Change sets can be written in .xml or .js
extension files.
How to write JavaScript changeset logs : [Click here ](https://github.com/mongeez/mongeez/wiki/JavaScript-Change-Log-Format)

## Version
 Grails 3.2.0

## How it works
 * First add a dependency for the plugin in build.gradle

```
#!groovy


      dependencies {
      ...
              compile "org.grails:grails-mongeez:0.2.4"
}
```


   
   
 * Create a directory 'grails-app/migrations' and place all your migrations here in JavaScript format.
 * Add below code segment in build.gradle of your project.
 * Run app using `grails run-app` or `grails war` 
 * Resources with the changeset logs inside migrations get executed.



```
#!groovy

sourceSets {
    main {
        output.dir("$buildDir/resources/main/migrations", builtBy: 'createMigrationDirectory')
    }
}
task createMigrationDirectory {
    doFirst {
        File migrationFilePathInstance = new File("$buildDir/resources/main", "migrations")
        if (!migrationFilePathInstance.exists()) {
            migrationFilePathInstance.mkdir()
        }
    }
    dependsOn 'copyMigrations'
}
task copyMigrations(type: Copy) {
    from 'grails-app/migrations/'
    into 'build/resources/main/migrations/'
}
```


In above code segment, `sourceSets` tells the gradle about migration folder which is created after the execution of the task ` createMigrationDirectory` and it depends on `copyMigrations` task which copies all the migration resources from your project directory `grails-app/migrations` to `build/resources/main/migrations/` location. Now migration resources available on classpath and grails-mongeez-0.2.4 plugin uses `org.reflections.Reflections` library which scans for resources in the classpath and returns `.js` resources of `migrations`.

## Ported By

[CauseCode Technologies](https://causecode.com/)

## Credits

David M. Carr, Commerce Technologies  [Click here](https://grails.org/plugin/mongeez)