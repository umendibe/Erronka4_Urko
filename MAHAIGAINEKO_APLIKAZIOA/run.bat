@echo off
echo Konpilatzen...
mkdir bin 2>nul
javac -cp "lib\mysql-connector-j-9.6.0.jar;lib\gson-2.10.1.jar" -d bin src\App.java src\Produktuak.java
if %errorlevel% neq 0 (
    echo ERRORE: konpilazioak huts egin du.
    pause
    exit /b 1
)
echo Exekutatzen...
java -cp "bin;lib\mysql-connector-j-9.6.0.jar;lib\gson-2.10.1.jar" App
pause
