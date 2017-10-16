@REM middleware launcher script
@REM
@REM Environment:
@REM JAVA_HOME - location of a JDK home dir (optional if java on path)
@REM CFG_OPTS  - JVM options (optional)
@REM Configuration:
@REM MIDDLEWARE_config.txt found in the MIDDLEWARE_HOME.
@setlocal enabledelayedexpansion

@echo off
if "%MIDDLEWARE_HOME%"=="" set "MIDDLEWARE_HOME=%~dp0\\.."
set ERROR_CODE=0

set "APP_LIB_DIR=%MIDDLEWARE_HOME%\lib\"

rem Detect if we were double clicked, although theoretically A user could
rem manually run cmd /c
for %%x in (%cmdcmdline%) do if %%~x==/c set DOUBLECLICKED=1

rem FIRST we load the config file of extra options.
set "CFG_FILE=%MIDDLEWARE_HOME%\MIDDLEWARE_config.txt"
set CFG_OPTS=
if exist %CFG_FILE% (
  FOR /F "tokens=* eol=# usebackq delims=" %%i IN ("%CFG_FILE%") DO (
    set DO_NOT_REUSE_ME=%%i
    rem ZOMG (Part #2) WE use !! here to delay the expansion of
    rem CFG_OPTS, otherwise it remains "" for this loop.
    set CFG_OPTS=!CFG_OPTS! !DO_NOT_REUSE_ME!
  )
)

rem We use the value of the JAVACMD environment variable if defined
set _JAVACMD=%JAVACMD%

if "%_JAVACMD%"=="" (
  if not "%JAVA_HOME%"=="" (
    if exist "%JAVA_HOME%\bin\java.exe" set "_JAVACMD=%JAVA_HOME%\bin\java.exe"
  )
)

if "%_JAVACMD%"=="" set _JAVACMD=java

rem Detect if this java is ok to use.
for /F %%j in ('"%_JAVACMD%" -version  2^>^&1') do (
  if %%~j==java set JAVAINSTALLED=1
  if %%~j==openjdk set JAVAINSTALLED=1
)

rem BAT has no logical or, so we do it OLD SCHOOL! Oppan Redmond Style
set JAVAOK=true
if not defined JAVAINSTALLED set JAVAOK=false

if "%JAVAOK%"=="false" (
  echo.
  echo A Java JDK is not installed or can't be found.
  if not "%JAVA_HOME%"=="" (
    echo JAVA_HOME = "%JAVA_HOME%"
  )
  echo.
  echo Please go to
  echo   http://www.oracle.com/technetwork/java/javase/downloads/index.html
  echo and download a valid Java JDK and install before running middleware.
  echo.
  echo If you think this message is in error, please check
  echo your environment variables to see if "java.exe" and "javac.exe" are
  echo available via JAVA_HOME or PATH.
  echo.
  if defined DOUBLECLICKED pause
  exit /B 1
)


rem We use the value of the JAVA_OPTS environment variable if defined, rather than the config.
set _JAVA_OPTS=%JAVA_OPTS%
if "%_JAVA_OPTS%"=="" set _JAVA_OPTS=%CFG_OPTS%

rem We keep in _JAVA_PARAMS all -J-prefixed and -D-prefixed arguments
rem "-J" is stripped, "-D" is left as is, and everything is appended to JAVA_OPTS
set _JAVA_PARAMS=

:param_beforeloop
if [%1]==[] goto param_afterloop
set _TEST_PARAM=%~1

rem ignore arguments that do not start with '-'
if not "%_TEST_PARAM:~0,1%"=="-" (
  shift
  goto param_beforeloop
)

set _TEST_PARAM=%~1
if "%_TEST_PARAM:~0,2%"=="-J" (
  rem strip -J prefix
  set _TEST_PARAM=%_TEST_PARAM:~2%
)

if "%_TEST_PARAM:~0,2%"=="-D" (
  rem test if this was double-quoted property "-Dprop=42"
  for /F "delims== tokens=1-2" %%G in ("%_TEST_PARAM%") DO (
    if not "%%G" == "%_TEST_PARAM%" (
      rem double quoted: "-Dprop=42" -> -Dprop="42"
      set _JAVA_PARAMS=%%G="%%H"
    ) else if [%2] neq [] (
      rem it was a normal property: -Dprop=42 or -Drop="42"
      set _JAVA_PARAMS=%_TEST_PARAM%=%2
      shift
    )
  )
) else (
  rem a JVM property, we just append it
  set _JAVA_PARAMS=%_TEST_PARAM%
)

:param_loop
shift

if [%1]==[] goto param_afterloop
set _TEST_PARAM=%~1

rem ignore arguments that do not start with '-'
if not "%_TEST_PARAM:~0,1%"=="-" goto param_loop

set _TEST_PARAM=%~1
if "%_TEST_PARAM:~0,2%"=="-J" (
  rem strip -J prefix
  set _TEST_PARAM=%_TEST_PARAM:~2%
)

if "%_TEST_PARAM:~0,2%"=="-D" (
  rem test if this was double-quoted property "-Dprop=42"
  for /F "delims== tokens=1-2" %%G in ("%_TEST_PARAM%") DO (
    if not "%%G" == "%_TEST_PARAM%" (
      rem double quoted: "-Dprop=42" -> -Dprop="42"
      set _JAVA_PARAMS=%_JAVA_PARAMS% %%G="%%H"
    ) else if [%2] neq [] (
      rem it was a normal property: -Dprop=42 or -Drop="42"
      set _JAVA_PARAMS=%_JAVA_PARAMS% %_TEST_PARAM%=%2
      shift
    )
  )
) else (
  rem a JVM property, we just append it
  set _JAVA_PARAMS=%_JAVA_PARAMS% %_TEST_PARAM%
)
goto param_loop
:param_afterloop

set _JAVA_OPTS=%_JAVA_OPTS% %_JAVA_PARAMS%
:run
 
set "APP_CLASSPATH=%APP_LIB_DIR%\net.devsupport.middleware.middleware-2.1.0-SNAPSHOT.jar;%APP_LIB_DIR%\net.devsupport.middleware.middleware-groups-2.1.0-SNAPSHOT.jar;%APP_LIB_DIR%\net.devsupport.middleware.middleware-monitoring-2.1.0-SNAPSHOT.jar;%APP_LIB_DIR%\net.devsupport.middleware.middleware-runtime-control-2.1.0-SNAPSHOT.jar;%APP_LIB_DIR%\org.scala-lang.scala-library-2.11.8.jar;%APP_LIB_DIR%\com.typesafe.config-1.2.1.jar;%APP_LIB_DIR%\net.ceedubs.ficus_2.11-1.1.2.jar;%APP_LIB_DIR%\com.typesafe.akka.akka-actor_2.11-2.3.9.jar;%APP_LIB_DIR%\com.typesafe.akka.akka-camel_2.11-2.3.9.jar;%APP_LIB_DIR%\com.typesafe.akka.akka-slf4j_2.11-2.3.9.jar;%APP_LIB_DIR%\org.slf4j.slf4j-api-1.7.5.jar;%APP_LIB_DIR%\org.apache.camel.camel-core-2.10.3.jar;%APP_LIB_DIR%\com.typesafe.akka.akka-remote_2.11-2.3.9.jar;%APP_LIB_DIR%\org.uncommons.maths.uncommons-maths-1.2.2a.jar;%APP_LIB_DIR%\com.typesafe.akka.akka-cluster_2.11-2.3.9.jar;%APP_LIB_DIR%\com.typesafe.akka.akka-contrib_2.11-2.3.9.jar;%APP_LIB_DIR%\com.typesafe.akka.akka-persistence-experimental_2.11-2.3.9.jar;%APP_LIB_DIR%\org.iq80.leveldb.leveldb-0.5.jar;%APP_LIB_DIR%\org.iq80.leveldb.leveldb-api-0.5.jar;%APP_LIB_DIR%\org.fusesource.leveldbjni.leveldbjni-all-1.7.jar;%APP_LIB_DIR%\org.fusesource.leveldbjni.leveldbjni-1.7.jar;%APP_LIB_DIR%\org.fusesource.hawtjni.hawtjni-runtime-1.8.jar;%APP_LIB_DIR%\org.fusesource.leveldbjni.leveldbjni-osx-1.5.jar;%APP_LIB_DIR%\org.fusesource.leveldbjni.leveldbjni-linux32-1.5.jar;%APP_LIB_DIR%\org.fusesource.leveldbjni.leveldbjni-linux64-1.5.jar;%APP_LIB_DIR%\org.fusesource.leveldbjni.leveldbjni-win32-1.5.jar;%APP_LIB_DIR%\org.fusesource.leveldbjni.leveldbjni-win64-1.5.jar;%APP_LIB_DIR%\com.github.krasserm.akka-persistence-cassandra_2.11-0.3.7.jar;%APP_LIB_DIR%\com.datastax.cassandra.cassandra-driver-core-2.1.5.jar;%APP_LIB_DIR%\io.netty.netty-3.9.0.Final.jar;%APP_LIB_DIR%\com.google.guava.guava-14.0.1.jar;%APP_LIB_DIR%\com.codahale.metrics.metrics-core-3.0.2.jar;%APP_LIB_DIR%\net.devsupport.middleware.middleware-api_2.11-1.0.0-SNAPSHOT.jar;%APP_LIB_DIR%\de.hawhamburg.csti.messaging.jvm-middleware-api-0.0.1.jar;%APP_LIB_DIR%\com.google.protobuf.protobuf-java-2.6.1.jar;%APP_LIB_DIR%\io.netty.netty-all-4.0.35.Final.jar;%APP_LIB_DIR%\org.apache.commons.commons-lang3-3.4.jar;%APP_LIB_DIR%\de.hawhamburg.csti.messaging.jvm-json-parser-0.1.0-SNAPSHOT.jar;%APP_LIB_DIR%\de.hawhamburg.csti.messaging.jvm-messaging-0.1.0-SNAPSHOT.jar;%APP_LIB_DIR%\org.spire-math.jawn-parser_2.11-0.8.4.jar;%APP_LIB_DIR%\de.hawhamburg.csti.messaging.tunnel-api-1.0.0-SNAPSHOT.jar;%APP_LIB_DIR%\de.hawhamburg.csti.messaging.jvm-serialization-0.0.2-SNAPSHOT.jar;%APP_LIB_DIR%\io.circe.circe-core_2.11-0.2.1.jar;%APP_LIB_DIR%\org.typelevel.export-hook_2.11-1.0.2.jar;%APP_LIB_DIR%\org.typelevel.macro-compat_2.11-1.0.4.jar;%APP_LIB_DIR%\org.spire-math.cats-core_2.11-0.2.0.jar;%APP_LIB_DIR%\org.spire-math.cats-macros_2.11-0.2.0.jar;%APP_LIB_DIR%\com.github.mpilquist.simulacrum_2.11-0.4.0.jar;%APP_LIB_DIR%\org.spire-math.algebra_2.11-0.3.1.jar;%APP_LIB_DIR%\org.spire-math.algebra-std_2.11-0.3.1.jar;%APP_LIB_DIR%\org.typelevel.machinist_2.11-0.4.1.jar;%APP_LIB_DIR%\org.scala-lang.scala-reflect-2.11.7.jar;%APP_LIB_DIR%\io.circe.circe-generic_2.11-0.2.1.jar;%APP_LIB_DIR%\com.chuusai.shapeless_2.11-2.2.5.jar"
set "APP_MAIN_CLASS=net.devsupport.middleware.Main"

rem Call the application and pass all arguments unchanged.
"%_JAVACMD%" %_JAVA_OPTS% %MIDDLEWARE_OPTS% -cp "%APP_CLASSPATH%" %APP_MAIN_CLASS% %*
if ERRORLEVEL 1 goto error
goto end

:error
set ERROR_CODE=1

:end

@endlocal

exit /B %ERROR_CODE%
