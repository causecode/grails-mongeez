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

includeTargets << grailsScript("_GrailsBootstrap")

target(main: "Run mongeez to bring the database up-to-date") {
    depends(configureProxy, packageApp, classpath, loadApp, configureApp)
    def mongeez = appCtx.getBean('mongeez')
    def dbName = mongeez.dbName
    if (!mongeez.executeEnabled) {
        mongeez.execute()
    }
    event("StatusFinal", ["Mongeez processed database " + dbName + "."])
}

setDefaultTarget(main)
