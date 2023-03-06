@echo off

rem Inicie o consumer-1.0.0.jar
start java -jar consumer-1.0.0.jar

rem Inicie o producer-1.0.0.jar
start java -jar producer-1.0.0.jar

rem Mantenha o processo de batch em execução, impedindo que o container se encerre
:loop
timeout /t 60
goto loop
