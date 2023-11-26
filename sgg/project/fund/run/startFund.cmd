@echo off
>nul 2>&1 "%SYSTEMROOT%\system32\cacls.exe" "%SYSTEMROOT%\system32\config\system"
if '%errorlevel%' NEQ '0' (
goto UACPrompt
) else ( goto gotAdmin )
:UACPrompt
echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
echo UAC.ShellExecute "%~s0", "", "", "runas", 1 >> "%temp%\getadmin.vbs"
"%temp%\getadmin.vbs"
exit /B
:gotAdmin
if exist "%temp%\getadmin.vbs" ( del "%temp%\getadmin.vbs" )
@echo off
cd /d %~dp0
c:
cd C:\Users\zhuwb\Desktop\fund
start javaw -Xms512m -Xmx512m -Dfile.encoding=UTF-8 -DhistorySleepTime=150 -DsleepTime=40 -DhistoryExecMaxTime=10800000 -jar fund-2.2.2.RELEASE.jar
start javaw -Xms512m -Xmx512m -Dfile.encoding=UTF-8 -jar xxl-job-admin-2.4.0-SNAPSHOT.jar
exit