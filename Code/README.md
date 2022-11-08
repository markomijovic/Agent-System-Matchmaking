Requires:

1. \lib\jade.jar

### Compile:
javac -classpath lib\jade.jar -d classes src\main\java\*.java

### Run:
java -cp lib/jade.jar;classes jade.Boot -gui -agents project:ProjectAgent
