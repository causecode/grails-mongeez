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

import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import com.mongodb.QueryBuilder
import org.apache.commons.lang3.time.DateFormatUtils
import org.mongeez.dao.MongeezDao
import org.mongeez.reader.ChangeSetReaderFactory

import java.text.SimpleDateFormat

class MongeezService {
    static transactional = false

    def changeSetFileProvider
    def mongeez
    def mongo

    private def getMongeezCollection() {
        def dbName = mongeez.getDbName()
        def db = mongo.getDB(dbName)
        def dbColl =  db.getCollection('mongeez')
        return dbColl
    }

    def getPendingChangeSets() {
        // TODO: use mongeez to get this information
        def pendingChangeSets = []
        def changeSetReaderFactory = ChangeSetReaderFactory.getInstance()
        def dao = new MongeezDao(mongo, mongeez.getDbName())
        def changeSetFiles = changeSetFileProvider.getChangeSetFiles()
        changeSetFiles.each { changeSetFile ->
            def changeSetReader = changeSetReaderFactory.getChangeSetReader(changeSetFile)
            def changeSets = changeSetReader.getChangeSets(changeSetFile)
            changeSets.each { changeSet ->
                if(changeSet.isRunAlways() || !dao.wasExecuted(changeSet)) {
                    pendingChangeSets.add(changeSet)
                }
            }
        }
        return pendingChangeSets
    }

    def getChangeSetExecutions() {
        // TODO: use mongeez to get this information
        def dbColl = getMongeezCollection()
        def query = QueryBuilder.start('type').is('changeSetExecution').get()
        // TODO: get a single way to determine the true order of existing executions... finer grained date
        def sort = new BasicDBObject([date: 1, resourcePath: 1])
        def changeSetExecutions = dbColl.find(query).sort((DBObject)sort).toArray()
        def dateFormatter = DateFormatUtils.ISO_DATE_TIME_ZONE_FORMAT
        def datePattern = dateFormatter.getPattern()
        def dateParser = new SimpleDateFormat(datePattern)
        changeSetExecutions.each { execution ->
            def origDate = execution.date
            def newDate = origDate[0..-4] + origDate[-2..-1] // Strip out colon in timezone
            execution.dateObj = dateParser.parse(newDate)
        }
    }
}
