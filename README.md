# Grails MongoDB Database Migration Plugin
Latest Version (1.1.0)

## Overview

This MongoDB database migration plugin helps to manage database changes while developing Grails 3.3.x and 3.2.x Applications with MongoDB.
This plugin uses the [Mongeez](https://github.com/mongeez/mongeez) library and change sets are written in JavaScript and XML.

[How to write changelogs](https://github.com/mongeez/mongeez/wiki/How-to-use-mongeez)


## Installation
 * First add a dependency for the plugin in build.gradle

Grails App >= 3.3.x use `v1.0.0` and <=3.2.x use `v0.2.4`
```
    buildscript {
        dependencies {
            ...
            classpath "com.causecode.plugins:grails-mongeez:$version"
        }
    }

    apply plugin:'grails-mongeez'
```


 * Create a directory `migrations` under `grails-app` and place all your migrations here in JavaScript or XML files.

 * For logging, add `grails.mongeez` package to your logger configuration. Example:

```
logger('grails.mongeez', DEBUG, ['STDOUT'], false)
```

## Configurations for protected database

Mongeez internally calls db.eval() method to execute queries.

> If authorization is enabled, you must have access to
all actions on all resources in order to run eval. Providing such access is not recommended, but if your organization
requires a user to run eval, create a role that grants anyAction on anyResource.
Do not assign this role to any other user.

Reference: https://docs.mongodb.com/manual/reference/method/db.eval/#access-control

**Use these commands to create your user and role:**
```
> use admin;
> db.createRole({ role:"<role_name>", privileges: [{ resource: { anyResource: true }, actions: ["anyAction"] }], roles: [] });

Ex: db.createRole({ role:"ExecuteMigrations",privileges:[{ resource: { anyResource: true }, actions: ["anyAction"] }], roles: [] });


This creates a role with given priviledges. Now create a user and assign this role to it.
> use <your_database> // The database you want to protect.
> db.createUser({ user: "<username>", pwd: "<password>", roles: [ { role: "<role_name>", db: "admin" } ]})

Ex.: db.createUser({ user: "mongeezRunner", pwd: "mongeezRunner", roles: [ { role: "executeMigrations", db: "admin" } ]})
```

Now you have a user `mongeezRunner` who can run these migrations on your protected database.

**application.yml configurations**

```
grails:
    mongodb:
        host: localhost
        port: 27017
        username: "username"
        password: "password"
        databaseName: "auth_test"
        authenticationDatabase: "auth_test"

    # Separate user to run migrations.
    mongeezCreds:
        username: "mongeezRunner"
        password: "mongeezRunner"
        databaseName: "auth_test"
        authenticationDatabase: "auth_test"
```

Note:
1. If you do not have separate user to run these migrations, then you don't need to specify `mongeezCreds`.
The configuration is picked from `mongodb` section. See [MongeezGrailsPlugin](https://github.com/causecode/grails-mongeez/blob/master/src/main/groovy/grails/mongeez/MongeezGrailsPlugin.groovy) class for details.
2. If you have specified `mongeezCreds`, `mongodb` configurations will be ignored and migrations will be run by the
credentials provided in `mongeezCreds`.

## Ported By

[CauseCode Technologies Pvt Ltd](https://causecode.com/)

## Credits

David M. Carr, Commerce Technologies
[Grails 2.x Plugin](https://grails.org/plugin/mongeez)
