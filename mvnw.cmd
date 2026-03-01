@echo off
setlocal

set MAVEN_PROJECTBASEDIR=%~dp0

@REM Auto-detect JAVA_HOME from java.exe in PATH
for /f "delims=" %%i in ('where java 2^>nul') do (
    set "JAVA_PATH=%%~dpi"
    goto :foundJava
)
echo ERROR: No 'java' command could be found in your PATH.
goto error

:foundJava
@REM java.exe is in JAVA_HOME\bin, so go up one level
for %%i in ("%JAVA_PATH%\..") do set "JAVA_HOME=%%~fi"
echo Using JAVA_HOME: %JAVA_HOME%

:init
@REM Download Maven if not already downloaded
set MAVEN_HOME=%USERPROFILE%\.m2\wrapper\dists\apache-maven-3.9.6

if exist "%MAVEN_HOME%\bin\mvn.cmd" goto runMaven

echo Downloading Maven 3.9.6...
set DOWNLOAD_URL=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.zip
set MAVEN_ZIP=%USERPROFILE%\.m2\wrapper\dists\apache-maven-3.9.6.zip
mkdir "%USERPROFILE%\.m2\wrapper\dists" 2>NUL

powershell -Command "Invoke-WebRequest -Uri '%DOWNLOAD_URL%' -OutFile '%MAVEN_ZIP%' -UseBasicParsing"
if "%ERRORLEVEL%" NEQ "0" (
    echo ERROR: Failed to download Maven.
    goto error
)

echo Extracting Maven...
powershell -Command "Expand-Archive -Path '%MAVEN_ZIP%' -DestinationPath '%USERPROFILE%\.m2\wrapper\dists\temp_extract' -Force"
if "%ERRORLEVEL%" NEQ "0" (
    echo ERROR: Failed to extract Maven.
    goto error
)

@REM Move from nested apache-maven-3.9.6 folder to target
mkdir "%MAVEN_HOME%" 2>NUL
xcopy "%USERPROFILE%\.m2\wrapper\dists\temp_extract\apache-maven-3.9.6\*" "%MAVEN_HOME%\" /E /Y /Q >NUL
rmdir /S /Q "%USERPROFILE%\.m2\wrapper\dists\temp_extract" 2>NUL
del "%MAVEN_ZIP%" 2>NUL

echo Maven 3.9.6 installed successfully.

:runMaven
set "MAVEN_CMD=%MAVEN_HOME%\bin\mvn.cmd"
set "JAVA_HOME=%JAVA_HOME%"
"%MAVEN_CMD%" %*
if ERRORLEVEL 1 goto error
goto end

:error
set ERROR_CODE=1

:end
endlocal
exit /b %ERROR_CODE%
