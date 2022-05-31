@ECHO off
set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;../../Resources/Java/JUnit/junit-4.12.jar;../../Resources/Java/JUnit/hamcrest-core-1.3.jar

@REM setting ESC environment variable for color
for /F %%a in ('echo prompt $E ^| cmd') do (
  set "ESC=%%a"
)
SETLOCAL EnableDelayedExpansion
SET RED=!ESC![1;31m
SET GREEN=!ESC![1;32m
SET MAGENTA=!ESC![1;35m
SET BOLD=!ESC![1m
SET NC=!ESC![0m

SET arg=%1

@REM check if argument is empty, if so go to :run
if [%arg%]==[] goto :run

@REM check if argument is -g OR --graph, is so go to :graph
if %arg%==-g goto :graph
if %arg%==--graph goto :graph

@REM check if argument is -c OR --clear, if so go to :clear
if %arg%==-c goto :clear
if %arg%==--clear goto :clear

@REM check if argument is -h OR --help, if so go to :help
if %arg%==-h goto :help
if %arg%==--help goto :help

@REM COMPILE AND LAUNCH
:run
cd ./src
echo %MAGENTA%Importing graph classes from graph/%NC%
javac -d ../classes/ graph/Graph.java graph/UndirectedGraph.java graph/Vertex.java
echo %GREEN%Done%NC%&echo\

echo %MAGENTA%Importing classpath of MinimumHeap and compiling dijkstra package.%NC%
javac -d ../classes/ -cp ".:graph:../../ex3/src" dijkstra/*.java
echo %GREEN%Done%NC%&echo\

cd ../classes
echo %MAGENTA%Running Main...%NC%
java dijkstra/Main "../../Resources/Datasets/"
echo %GREEN%Done%NC%
cd ..
goto :end

@REM COMPILE AND LAUNCH GRAPH TESTS
:graph
cd ./src
echo %MAGENTA%Compiling...%NC%
javac -d "../classes" graph/Vertex.java
javac -d "../classes" graph/Graph.java
javac -d "../classes" -cp "%CLASSPATH%" graph/*.java
echo %GREEN%Done%NC%&echo\

cd ../classes
echo %MAGENTA%Running tests...%NC%
java -cp "%CLASSPATH%" graph/GraphTestsRunner
echo %GREEN%Done%NC%
cd ..
goto :end

@REM CLEAR
:clear
rmdir /s /q "classes/graph"
echo "%RED%classes/%NC%" cleared successfully
goto :end

@REM HELP
:help
echo &echo\?? %BOLD%HELP%NC% ??
echo    Use option -c or --clear to clear the %RED%classes/%NC% folder
echo    Use %BOLD%"run.bat"%NC% to compile and launch %BOLD%graph/GraphTestsRunner%NC%&echo\

@REM END
:end