<%--
  - Copyright 2012 David M. Carr, Commerce Technologies
  -
  - Licensed under the Apache License, Version 2.0 (the "License");
  - you may not use this file except in compliance with the License.
  - You may obtain a copy of the License at
  -
  -   http://www.apache.org/licenses/LICENSE-2.0
  -
  - Unless required by applicable law or agreed to in writing, software
  - distributed under the License is distributed on an "AS IS" BASIS,
  - WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  - See the License for the specific language governing permissions and
  - limitations under the License.
  --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Mongeez Pending Change Sets</title>
</head>
<body>
    <div class="mongeezChangeSetsPending">
        <g:if test="${pendingChangeSets}">
            <table class="mongeezChangeSetsPendingTable">
                <thead>
                    <tr>
                        <th class="column resourcePath">Resource Path</th>
                        <th class="column author">Author</th>
                        <th class="column changeId">Change ID</th>
                        <th class="column runAlways">Run Always</th>
                    </tr>
                </thead>
                <tbody>
                    <g:each in="${pendingChangeSets}" var="pendingChangeSet">
                        <tr>
                            <td class="column resourcePath">${pendingChangeSet.resourcePath}</td>
                            <td class="column author">${pendingChangeSet.author}</td>
                            <td class="column changeId">${pendingChangeSet.changeId}</td>
                            <td class="column runAlways">${pendingChangeSet.runAlways ? 'Yes' : 'No'}</td>
                        </tr>
                    </g:each>
                </tbody>
            </table>
        </g:if>
        <g:else>
            <div class="mongeezNoChangeSetsPending">Mongeez has executed all available change sets.</div>
        </g:else>
    </div>
</body>
</html>
