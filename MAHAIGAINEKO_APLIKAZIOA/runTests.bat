@echo off
REM Compilar los archivos Java
echo Compilando...
javac -cp "lib/*;bin" -d bin src\App.java src\Produktuak.java src\ProduktuakLogika.java
if errorlevel 1 (
    echo Error en la compilacion de archivos principales
    pause
    exit /b 1
)

javac -cp "lib/*;bin" -d bin src\AppTest.java
if errorlevel 1 (
    echo Error en la compilacion de tests
    pause
    exit /b 1
)

REM Ejecutar los tests
echo.
echo Ejecutando tests...
java -jar lib/junit-platform-console-standalone-1.13.0-M3.jar -cp=bin -cp=lib/gson-2.10.1.jar -cp=lib/mysql-connector-j-9.6.0.jar --scan-classpath

pause
