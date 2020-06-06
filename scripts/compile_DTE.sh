#!/bin/bash
echo "Run Departure Terminal Entrance"
rm -r ../out/outDTE;
mkdir ../out/outDTE;
javac -d "../out/outDTE" commonInfra/*.java entities/*.java proxies/*.java sharedRegions/*.java stubs/*.java  MainProgram/SharedRegionMain.java
java -classpath "../out/outDTE" MainProgram.SharedRegionMain DepTerminalEntrance