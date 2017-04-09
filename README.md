# Grails MongoDB Database Migration Plugin
Latest Version (0.2.4)

## Overview 

This MongoDB database migration plugin helps to manage database changes while developing Grails 3.2.x Applications with MongoDB.
This plugin uses the [Mongeez](https://github.com/mongeez/mongeez) library and change sets are written in JavaScript and XML.

[How to write changelogs](https://github.com/mongeez/mongeez/wiki/How-to-use-mongeez)


## Installation
 * First add a dependency for the plugin in build.gradle

```
    buildscript {
        dependencies {
            ...
            classpath "com.causecode.plugins:grails-mongeez:0.2.4"
        }
    }

    apply plugin:'grails-mongeez'
```


 * Create a directory `migrations` under `grails-app` and place all your migrations here in JavaScript or XML files.
 
 * For logging, add `grails.mongeez` package to your logger configuration. Example:

```
logger('grails.mongeez', DEBUG, ['STDOUT'], false)
```

`Note: This plugin is yet not tested with Grails 3.x and 3.1.x Apps but it should work fine for them as well.`

## Ported By

[CauseCode Technologies](https://causecode.com/)

## Credits

David M. Carr, Commerce Technologies
[Grails 2.x Plugin](https://grails.org/plugin/mongeez)