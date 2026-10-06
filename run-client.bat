@echo off
setlocal

title Evon Client

rem Always operate from the folder this script lives in, so it can be
rem double-clicked from anywhere.
cd /d "%~dp0"

if not exist "gradlew.bat" (
    echo [ERROR] gradlew.bat was not found next to this script.
    echo         Put run-client.bat inside the OpenEvon folder.
    echo.
    pause
    exit /b 1
)

echo ============================================
echo   Evon - building and launching the client
echo ============================================
echo.
echo First launch takes a while (Gradle sets up the dev
echo environment and downloads Minecraft assets once).
echo.

call gradlew.bat runClient
set "EXITCODE=%ERRORLEVEL%"

echo.
if "%EXITCODE%"=="0" (
    echo Client closed.
) else (
    echo [ERROR] The client exited with code %EXITCODE%.
    echo         See the output above, or: run\logs\latest.log
    echo         Crash reports: run\crash-reports\
)
echo.
pause
exit /b %EXITCODE%
