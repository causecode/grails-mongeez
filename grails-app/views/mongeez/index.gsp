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
    <title>Mongeez Status</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.1/jquery.min.js"></script>
</head>
<body>
    <div class="mongeezStatus">
        <g:if test="${flash.message}">
            <div class="globalMessage">${flash.message}</div>
        </g:if>

        <g:if test="${pendingChangeSets}">
            <g:form action="run" class="mongeezRunUpdateForm">
                <g:submitButton name="Run Mongeez" class="mongeezRunUpdateButton"/>
            </g:form>
        </g:if>

        <g:render template="pendingChangeSets" model="[pendingChangeSets: pendingChangeSets]"/>

        <g:render template="executedChangeSets" model="[changeSetExecutions: changeSetExecutions]"/>
    </div>
</body>
</html>
