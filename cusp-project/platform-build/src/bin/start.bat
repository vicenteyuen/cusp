@echo off
rem Licensed to the Apache Software Foundation (ASF) under one or more
rem contributor license agreements.  See the NOTICE file distributed with
rem this work for additional information regarding copyright ownership.
rem The ASF licenses this file to You under the Apache License, Version 2.0
rem (the "License"); you may not use this file except in compliance with
rem the License.  You may obtain a copy of the License at
rem
rem     http://www.apache.org/licenses/LICENSE-2.0
rem
rem Unless required by applicable law or agreed to in writing, software
rem distributed under the License is distributed on an "AS IS" BASIS,
rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
rem See the License for the specific language governing permissions and
rem limitations under the License.

rem ---------------------------------------------------------------------------
rem Start script for the Custom Unify Service Server
rem ---------------------------------------------------------------------------

setlocal

rem Guess CUSP_HOME if not defined
set "CURRENT_DIR=%cd%"
if not "%CUSP_HOME%" == "" goto gotHome
set "CUSP_HOME=%CURRENT_DIR%"
if exist "%CUSP_HOME%\bin\start.bat" goto okHome
cd ..
set "CUSP_HOME=%cd%"
cd "%CURRENT_DIR%"

:gotHome
echo %CUSP_HOME%
if exist "%CUSP_HOME%\bin\start.bat" goto okHome
echo The CUSP_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
goto end

:okHome
rem Copy CATALINA_BASE from CATALINA_HOME if not defined
if not "%CUSP_HOME%" == "" goto gotBase
set "CUSP_BASE=%CUSP_HOME%"

:gotBase
rem Ensure that any user defined CLASSPATH variables are not used on startup,
rem but allow them to be specified in setenv.bat, in rare case when it is needed.
if "%CLASSPATH%" == "" goto emptyClasspath
set "CLASSPATH=%CLASSPATH%;"
:emptyClasspath
set "CLASSPATH=%CLASSPATH%;%CUSP_HOME%\bin\bootlib\bootstrap-0.0.1-SNAPSHOT.jar;%CUSP_HOME%\bin\bootlib\tomcat-embed-logging-juli-9.0.0.M6.jar;"

rem ----- Execute The Requested Command ---------------------------------------
echo Using CUSP_BASE:   "%CUSP_BASE%"
echo Using CUSP_HOME:   "%CUSP_HOME%"

if ""%1"" == ""debug"" goto use_jdk
echo Using JRE_HOME:        "%JRE_HOME%"
goto java_dir_displayed
:use_jdk
echo Using JAVA_HOME:       "%JAVA_HOME%"

:java_dir_displayed
echo Using CLASSPATH:       "%CLASSPATH%"

set _EXECJAVA="Java"
set MAINCLASS=org.vsg.cusp.startup.Bootstrap
set ACTION=start


rem Execute Java with the applicable properties
%_EXECJAVA% -classpath "%CLASSPATH%" -Dcusp.base="%CUSP_BASE%" -Dcusp.home="%CUSP_HOME%" %MAINCLASS% %ACTION%
goto end

:end