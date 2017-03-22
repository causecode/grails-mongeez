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

package grails.plugin.mongeez

import org.mongeez.reader.ChangeSetFileProvider
import org.springframework.core.io.Resource
import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import java.util.regex.Pattern
import org.springframework.core.io.ClassPathResource

class ReflectionsChangeSetFileProvider implements ChangeSetFileProvider {
    List<Resource> getChangeSetFiles() {
        // TODO Consider using pre-scanned metadata, at least when packaged as a war
        Reflections reflections = new Reflections('migrations', new ResourcesScanner())
        def migrationResourcePaths = reflections.getResources(Pattern.compile('.*(\\.js|\\.xml)'))
        def migrationResources = migrationResourcePaths.sort().collect{new ClassPathResource(it)}
        return migrationResources
    }
}
