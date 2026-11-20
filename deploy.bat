@echo off
setlocal

set "PROJECT_DIR=%~dp0"
set "JAVA_HOME=C:\Java"
set "TOMCAT_HOME=D:\Program_oD\apache-tomcat-9.0.124"
set "CONNECTOR_J=D:\Program_oD\mysql-connector-j-26.10.0\mysql-connector-j-26.10.0.jar"
set "OUTPUT_DIR=%PROJECT_DIR%WebContent\WEB-INF\classes"
set "DEPLOY_DIR=%TOMCAT_HOME%\webapps\GaraotoJava"

echo [1/2] Dang bien dich Java...
if not exist "%OUTPUT_DIR%" mkdir "%OUTPUT_DIR%"
"%JAVA_HOME%\bin\javac.exe" -encoding UTF-8 -cp "%TOMCAT_HOME%\lib\servlet-api.jar;%CONNECTOR_J%" -d "%OUTPUT_DIR%" ^
  "%PROJECT_DIR%src\com\garaoto\DB.java" ^
  "%PROJECT_DIR%src\com\garaoto\PayrollRow.java" ^
  "%PROJECT_DIR%src\com\garaoto\PayrollDAO.java" ^
  "%PROJECT_DIR%src\com\garaoto\PayrollServlet.java" ^
  "%PROJECT_DIR%src\com\garaoto\DetailServlet.java" ^
  "%PROJECT_DIR%src\com\garaoto\UpdateServlet.java" ^
  "%PROJECT_DIR%src\com\garaoto\LockServlet.java"
if errorlevel 1 (
    echo Bien dich that bai. Kiem tra loi o tren.
    pause
    exit /b 1
)

echo [2/2] Dang deploy vao Tomcat...
if not exist "%DEPLOY_DIR%" mkdir "%DEPLOY_DIR%"
xcopy "%PROJECT_DIR%WebContent\*" "%DEPLOY_DIR%\" /E /I /Y /Q >nul

echo.
echo Deploy thanh cong.
echo Hay Stop roi Start Tomcat 9.x trong VS Code, sau do tai lai trinh duyet.
pause
